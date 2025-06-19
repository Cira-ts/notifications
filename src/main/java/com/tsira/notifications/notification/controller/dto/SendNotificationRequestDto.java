package com.tsira.notifications.notification.controller.dto;

import com.tsira.notifications.address.repository.enums.AddressType;
import com.tsira.notifications.notificationpreference.repository.enums.NotificationType;
import jakarta.validation.constraints.NotNull;

public record SendNotificationRequestDto(
        @NotNull
        Long customerId,

        @NotNull
        NotificationType type,

        @NotNull
        AddressType addressType,

        String subject,

        String messageBody
) {

}
