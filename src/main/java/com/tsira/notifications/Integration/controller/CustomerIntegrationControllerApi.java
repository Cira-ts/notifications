package com.tsira.notifications.Integration.controller;

import com.tsira.notifications.Integration.controller.dto.CustomerUpdateDto;
import com.tsira.notifications.address.controller.dto.AddressGetDto;
import com.tsira.notifications.address.repository.enums.AddressType;
import com.tsira.notifications.common.paginationandsort.PageAndSortCriteria;
import com.tsira.notifications.common.paginationandsort.PageView;
import com.tsira.notifications.notificationpreference.controller.dto.PreferenceGetDto;
import com.tsira.notifications.notificationpreference.repository.enums.NotificationType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Customer Integration Management", description = "Operations related to managing Integration Customer.")

public interface CustomerIntegrationControllerApi {

    @Operation(summary = "Get Preferences", description = "Retrieve a paginated list of preferences. Required role: `USER`")
    PageView<PreferenceGetDto> getPreferences(
            @Valid PageAndSortCriteria pageAndSortCriteria,
            @RequestParam(required = false) NotificationType notificationType,
            @RequestParam(required = false) Boolean optedIn,
            @RequestParam(required = false) String search );

    @Operation(summary = "Get Addresses", description = "Retrieve a paginated list of addresses. Required role: `USER`")
    PageView<AddressGetDto> getAddresses(
            @Valid PageAndSortCriteria pageAndSortCriteria,
            @RequestParam(required = false) AddressType addressType,
            @RequestParam(required = false) LocalDate validTillFrom,
            @RequestParam(required = false) LocalDate validTillTo,
            @RequestParam(required = false) String search );

    @Operation(summary = "Batch Update Customers", description = "Batch Update Customers. Required role: `USER`")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Customers Updated successfully",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Customer exists",
                    content = @Content(
                            examples = {
                                    @ExampleObject(
                                            name = "unique_customer_email",
                                            value = "{\"errorCode\": \"unique_customer_email\"}",
                                            description = "customer email exists"
                                    ),
                                    @ExampleObject(
                                            name = "Some_customers_not_found",
                                            value = "{\"errorCode\": \"Some_customers_not_found\"}",
                                            description = "Some customers not found"
                                    )
                            }
                    )
            )
    })
    ResponseEntity<Void> batchUpdateCustomers(@RequestBody List<CustomerUpdateDto> customers);
}
