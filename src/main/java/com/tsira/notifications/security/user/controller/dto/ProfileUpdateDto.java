package com.tsira.notifications.security.user.controller.dto;

import jakarta.validation.constraints.NotEmpty;

public record ProfileUpdateDto(
        @NotEmpty
        String firstName,

        @NotEmpty
        String lastName
) {
}
