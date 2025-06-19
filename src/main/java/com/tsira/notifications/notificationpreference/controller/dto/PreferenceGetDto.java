package com.tsira.notifications.notificationpreference.controller.dto;

import com.tsira.notifications.notificationpreference.repository.enums.NotificationType;

public interface PreferenceGetDto {
    Long getId();

    NotificationType getNotificationType();

    boolean isOptedIn();

    String getCustomerName();

    String getEmail();

    String getPhoneNumber();
}
