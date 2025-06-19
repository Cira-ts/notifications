package com.tsira.notifications.notification.controller;

import com.tsira.notifications.common.paginationandsort.PageAndSortCriteria;
import com.tsira.notifications.common.paginationandsort.PageView;
import com.tsira.notifications.notification.controller.dto.NotificationLogGetDto;
import com.tsira.notifications.notification.controller.dto.SendNotificationRequestDto;
import com.tsira.notifications.notification.repository.enums.NotificationStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Tag(name = "Notifications", description = "Operations related to managing notifications.")
public interface NotificationControllerApi {

    @Operation(
            summary = "Get Notification Statuses",
            description = "Fetch all available notification statuses. Required role: `ADMIN`."
    )
    NotificationStatus[] getNotificationStatuses();

    @Operation(
            summary = "Send Notifications",
            description = "Send Notifications. Required role: `ADMIN`."
    )
    ResponseEntity<Void> sendNotification(@RequestBody @Valid SendNotificationRequestDto request);

    @Operation(
            summary = "Get Notification Statuses",
            description = "Retrieve a paginated list of notification logs with optional filters. Required role: `ADMIN`."
    )
    PageView<NotificationLogGetDto> getNotificationLogs(
            @Valid PageAndSortCriteria pageAndSortCriteria,
            @RequestParam(required = false) NotificationStatus notificationStatus,
            @RequestParam(required = false) LocalDateTime sentTimeFrom,
            @RequestParam(required = false) LocalDateTime sentTimeTo,
            @RequestParam(required = false) String search);
}
