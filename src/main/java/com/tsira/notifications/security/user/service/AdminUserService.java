package com.tsira.notifications.security.user.service;

import com.tsira.notifications.Integration.users.dto.IntegrationUsersGetDto;
import com.tsira.notifications.common.paginationandsort.PageAndSortCriteria;
import com.tsira.notifications.security.user.controller.dto.PasswordChangeDto;
import com.tsira.notifications.security.user.controller.dto.ProfileUpdateDto;
import com.tsira.notifications.security.user.controller.dto.UserDetailsGetDTO;
import com.tsira.notifications.security.user.repository.entity.AppUser;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

public interface AdminUserService {
    AppUser lookUpActiveUserByEmail(String email);

    UserDetailsGetDTO getProfile();

    void updateUserProfile(ProfileUpdateDto dto);

    void changePassword(PasswordChangeDto dto);

    AppUser currentUser();

    Page<IntegrationUsersGetDto> getIntegrationUsers(@Valid PageAndSortCriteria pageAndSortCriteria, String search);

    void activateUser(Long id);
}
