package com.tsira.notifications.security.user.controller.dto;

import lombok.Builder;

@Builder
public record UserDetailsGetDTO(
        String firstName,
        String lastName,
        String email
) {
}
