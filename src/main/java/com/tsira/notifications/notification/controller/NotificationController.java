package com.tsira.notifications.notification.controller;


import com.tsira.notifications.common.paginationandsort.PageAndSortCriteria;
import com.tsira.notifications.common.paginationandsort.PageView;
import com.tsira.notifications.notification.controller.dto.NotificationLogGetDto;
import com.tsira.notifications.notification.controller.dto.SendNotificationRequestDto;
import com.tsira.notifications.notification.repository.enums.NotificationStatus;
import com.tsira.notifications.notification.service.NotificationLogService;
import com.tsira.notifications.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("admin/notifications")
@RequiredArgsConstructor
public class NotificationController implements NotificationControllerApi{

    private final NotificationService notificationService;
    private final NotificationLogService notificationLogService;

    @Override
    @GetMapping("types")
    @PreAuthorize("hasRole('ADMIN')")
    public NotificationStatus[] getNotificationStatuses() {
        return NotificationStatus.values();
    }

    @Override
    @PostMapping("send")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> sendNotification(@RequestBody @Valid SendNotificationRequestDto request) {
        notificationService.sendAndTrackNotification(request);
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping("logs")
    @PreAuthorize("hasRole('ADMIN')")
    public PageView<NotificationLogGetDto> getNotificationLogs(
            @Valid PageAndSortCriteria pageAndSortCriteria,
            @RequestParam(required = false) NotificationStatus notificationStatus,
            @RequestParam(required = false) LocalDateTime sentTimeFrom,
            @RequestParam(required = false) LocalDateTime sentTimeTo,
            @RequestParam(required = false) String search) {
        return PageView.of(notificationLogService.getNotificationLogs(
                pageAndSortCriteria,
                notificationStatus,
                sentTimeFrom,
                sentTimeTo,
                search));
    }
}
