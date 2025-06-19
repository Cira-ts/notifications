package com.tsira.notifications.notificationpreference.controller.dto;

import com.tsira.notifications.notificationpreference.repository.enums.NotificationType;
import jakarta.validation.constraints.NotNull;

public record PreferenceCreateDto(
        @NotNull
        Long customerId,

        @NotNull
        NotificationType type,

        @NotNull
        Boolean optedIn
) {
}
