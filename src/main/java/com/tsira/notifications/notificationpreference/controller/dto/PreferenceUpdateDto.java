package com.tsira.notifications.notificationpreference.controller.dto;

import com.tsira.notifications.notificationpreference.repository.enums.NotificationType;
import jakarta.validation.constraints.NotNull;

public record PreferenceUpdateDto(
        @NotNull
        NotificationType type,

        @NotNull
        Boolean optedIn
) {
}
