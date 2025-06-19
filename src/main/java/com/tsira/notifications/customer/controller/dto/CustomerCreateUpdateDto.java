package com.tsira.notifications.customer.controller.dto;

import com.tsira.notifications.common.AppConstants;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record CustomerCreateUpdateDto (
        @NotEmpty
        String name,

        @Pattern(regexp = AppConstants.EMAIL_PATTERN, message = "invalid_email")
        @NotEmpty
        String email,

        @NotEmpty
        String phoneNumber

) {
}
