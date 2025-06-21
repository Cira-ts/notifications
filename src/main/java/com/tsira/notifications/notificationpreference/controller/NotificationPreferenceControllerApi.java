package com.tsira.notifications.notificationpreference.controller;

import com.tsira.notifications.common.paginationandsort.PageAndSortCriteria;
import com.tsira.notifications.common.paginationandsort.PageView;
import com.tsira.notifications.notificationpreference.controller.dto.PreferenceCreateDto;
import com.tsira.notifications.notificationpreference.controller.dto.PreferenceGetDto;
import com.tsira.notifications.notificationpreference.controller.dto.PreferenceUpdateDto;
import com.tsira.notifications.notificationpreference.repository.enums.NotificationType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Notification Preference Management", description = "Operations related to managing notification preferences.")
public interface NotificationPreferenceControllerApi {

    @Operation(
            summary = "Get Notification Types",
            description = "Fetch all available notification types. Required role: `ADMIN`."
    )
    NotificationType[] getNotificationTypes();

    @Operation(
            summary = "Get Preferences",
            description = "Retrieve a paginated list of preferences with optional filters. Required role: `ADMIN`"
    )
    PageView<PreferenceGetDto> getPreferences(
            @Valid PageAndSortCriteria pageAndSortCriteria,
            @RequestParam(required = false) NotificationType notificationType,
            @RequestParam(required = false) Boolean optedIn,
            @RequestParam(required = false) String search );

    @Operation(
            summary = "Create Preference",
            description = "Create Preference. Required role: `ADMIN`"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Preference created successfully",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Preference exists",
                    content = @Content(
                            examples = {
                                    @ExampleObject(
                                            name = "preference_already_exists",
                                            value = "{\"errorCode\": \"preference_already_exists\"}",
                                            description = "preference already exists"
                                    )
                            }
                    )
            )
    })
    void createPreference(@RequestBody @Valid PreferenceCreateDto dto);

    @Operation(
            summary = "Update Preference",
            description = "Update Preference. Required role: `ADMIN`"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Preference updated successfully",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Preference exists",
                    content = @Content(
                            examples = {
                                    @ExampleObject(
                                            name = "preference_already_exists",
                                            value = "{\"errorCode\": \"preference_already_exists\"}",
                                            description = "preference already exists"
                                    )
                            }
                    )
            )
    })
    void updatePreference(@PathVariable Long id, @RequestBody @Valid PreferenceUpdateDto dto);

    @Operation(
            summary = "Delete Preference",
            description = "Delete Preference. Required role: `ADMIN`"
    )
    void deletePreference(@PathVariable Long id);
}
