package com.tsira.notifications.notification.controller.dto;


import com.tsira.notifications.notification.repository.enums.NotificationStatus;
import com.tsira.notifications.notificationpreference.repository.enums.NotificationType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface NotificationLogGetDto {
    Long getId();

    NotificationType getType();

    LocalDateTime getSentTime();

    LocalDate getValidTillDate();

    String getError();

    String getCustomerName();

    String getEmail();

    String getPhoneNumber();
}
