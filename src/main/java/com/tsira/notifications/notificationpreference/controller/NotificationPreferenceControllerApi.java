package com.tsira.notifications.notificationpreference.controller;

import com.tsira.notifications.common.paginationandsort.PageAndSortCriteria;
import com.tsira.notifications.common.paginationandsort.PageView;
import com.tsira.notifications.notificationpreference.controller.dto.PreferenceCreateDto;
import com.tsira.notifications.notificationpreference.controller.dto.PreferenceGetDto;
import com.tsira.notifications.notificationpreference.controller.dto.PreferenceUpdateDto;
import com.tsira.notifications.notificationpreference.repository.enums.NotificationType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
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
    void createPreference(@RequestBody @Valid PreferenceCreateDto dto);

    @Operation(
            summary = "Update Preference",
            description = "Update Preference. Required role: `ADMIN`"
    )
    void updatePreference(@PathVariable Long id, @RequestBody @Valid PreferenceUpdateDto dto);

    @Operation(
            summary = "Delete Preference",
            description = "Delete Preference. Required role: `ADMIN`"
    )
    void deletePreference(@PathVariable Long id);
}
