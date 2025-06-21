package com.tsira.notifications.address.controller;

import com.tsira.notifications.address.controller.dto.AddressCreateDto;
import com.tsira.notifications.address.controller.dto.AddressGetDto;
import com.tsira.notifications.address.controller.dto.AddressUpdateDto;
import com.tsira.notifications.address.repository.enums.AddressType;
import com.tsira.notifications.common.paginationandsort.PageAndSortCriteria;
import com.tsira.notifications.common.paginationandsort.PageView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "Addresses Management", description = "Operations related to managing addresses.")
public interface AddressControllerApi {

    @Operation(
            summary = "Get address types",
            description = "Fetch all available address types. Required role: `ADMIN`."
    )
    AddressType[] getAddressTypes();

    @Operation(
            summary = "Get Addresses",
            description = "Retrieve a paginated list of addresses with optional filters. Required role: `ADMIN`"
    )
    PageView<AddressGetDto> getAddresses(@Valid PageAndSortCriteria pageAndSortCriteria,
                                         @RequestParam(required = false) AddressType addressType,
                                         @RequestParam(required = false) LocalDate validTillFrom,
                                         @RequestParam(required = false) LocalDate validTillTo,
                                         @RequestParam(required = false) String search );

    @Operation(
            summary = "Create Address",
            description = "Required role: `ADMIN`"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Address created successfully",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Address exists",
                    content = @Content(
                            examples = {
                                    @ExampleObject(
                                            name = "address_already_exists",
                                            value = "{\"errorCode\": \"address_already_exists\"}",
                                            description = "address already exists"
                                    )
                            }
                    )
            )
    })
    void createAddress(@RequestBody @Valid AddressCreateDto dto);

    @Operation(
            summary = "Update Address",
            description = "Required role: `ADMIN`"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Address updated successfully",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Address exists",
                    content = @Content(
                            examples = {
                                    @ExampleObject(
                                            name = "address_already_exists",
                                            value = "{\"errorCode\": \"address_already_exists\"}",
                                            description = "address already exists"
                                    )
                            }
                    )
            )
    })
    void updateAddress(@PathVariable Long id, @RequestBody @Valid AddressUpdateDto dto);

    @Operation(
            summary = "Delete Address",
            description = "Delete a specific Address by its ID. Required role: `ADMIN`."
    )
    void deleteAddress(@PathVariable Long id);
}
