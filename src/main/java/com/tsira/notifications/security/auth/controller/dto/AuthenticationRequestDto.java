package com.tsira.notifications.security.auth.controller.dto;

import com.tsira.notifications.common.AppConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record AuthenticationRequestDto(
        @NotEmpty
        @Pattern(regexp = AppConstants.EMAIL_PATTERN)
        @Schema(description ="email", example = "john.doe@example.com")
        String email,

        @NotEmpty
        String password
) {
}
