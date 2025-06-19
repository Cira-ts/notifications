package com.tsira.notifications.notification.service;


import com.tsira.notifications.notification.controller.dto.SendNotificationRequestDto;

public interface NotificationService {
    void sendAndTrackNotification(SendNotificationRequestDto request);
}
