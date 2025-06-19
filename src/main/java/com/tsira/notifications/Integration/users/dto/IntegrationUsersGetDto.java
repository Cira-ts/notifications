package com.tsira.notifications.Integration.users.dto;


import com.tsira.notifications.security.user.repository.enums.UserStatus;

public interface IntegrationUsersGetDto {
    Long getId();
    String getFirstName();
    String getLastName();
    String getEmail();
    UserStatus getStatus();
}
