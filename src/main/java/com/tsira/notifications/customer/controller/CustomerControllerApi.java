package com.tsira.notifications.customer.controller;

import com.tsira.notifications.common.paginationandsort.PageAndSortCriteria;
import com.tsira.notifications.common.paginationandsort.PageView;
import com.tsira.notifications.customer.controller.dto.CustomerCreateUpdateDto;
import com.tsira.notifications.customer.controller.dto.CustomerDetailsGetDto;
import com.tsira.notifications.customer.controller.dto.CustomerGetDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Customer Management", description = "Operations related to managing customers.")
public interface CustomerControllerApi {

    @Operation(
            summary = "Get Customers",
            description = "Retrieve a paginated list of customers with optional filters. Required role: `ADMIN`."
    )
    PageView<CustomerGetDto> getCustomers(
            @Valid PageAndSortCriteria pageAndSortCriteria,
            @RequestParam(required = false) String search
    );

    @Operation(
            summary = "Get customer details",
            description = "Fetch customer details. Required role: `ADMIN`."
    )
    CustomerDetailsGetDto getCustomerDetails(@PathVariable Long id);

    @Operation(
            summary = "Create customer",
            description = "Create customer. Required role: `ADMIN`."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Customer created successfully",
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
                                    )
                            }
                    )
            )
    })
    void createCustomer(@RequestBody @Valid CustomerCreateUpdateDto dto);

    @Operation(
            summary = "Update customer",
            description = "Update customer. Required role: `ADMIN`."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Customer updated successfully",
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
                                    )
                            }
                    )
            )
    })
    void updateCustomers(@PathVariable Long id, @RequestBody @Valid CustomerCreateUpdateDto dto);

    @Operation(
            summary = "Delete customer",
            description = "Delete customer. Required role: `ADMIN`."
    )
    void deleteCustomers(@PathVariable Long id);
}
