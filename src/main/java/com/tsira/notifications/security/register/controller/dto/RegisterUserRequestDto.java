package com.tsira.notifications.security.register.controller.dto;

import com.tsira.notifications.common.AppConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RegisterUserRequestDto(
        @NotEmpty
        String firstName,

        @NotEmpty
        String lastName,

        @NotEmpty
        @Pattern(regexp = AppConstants.EMAIL_PATTERN)
        @Schema(description ="email", example = "john.doe@example.com")
        String email,

        @NotEmpty
        @Size(min = 6)
        String password
) {
}
