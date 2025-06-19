package com.tsira.notifications.security.user.service;

import com.tsira.notifications.Integration.users.dto.IntegrationUsersGetDto;
import com.tsira.notifications.common.AppConstants;
import com.tsira.notifications.common.paginationandsort.PageAndSortCriteria;
import com.tsira.notifications.exception.BusinessException;
import com.tsira.notifications.security.user.controller.dto.PasswordChangeDto;
import com.tsira.notifications.security.user.controller.dto.ProfileUpdateDto;
import com.tsira.notifications.security.user.controller.dto.UserDetailsGetDTO;
import com.tsira.notifications.security.user.repository.AdminUserRepository;
import com.tsira.notifications.security.user.repository.entity.AppUser;
import com.tsira.notifications.security.user.repository.enums.UserStatus;
import com.tsira.notifications.security.user.repository.enums.UserType;
import com.tsira.notifications.exception.SecurityViolationException;
import com.nulabinc.zxcvbn.Zxcvbn;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminUserServiceImp implements AdminUserService{

    private final PasswordEncoder passwordEncoder;
    private final AdminUserRepository userRepository;

    public AppUser lookUpActiveUserByEmail(String email) {
        return userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE)
                .orElseThrow(SecurityViolationException::new);
    }

    public UserDetailsGetDTO getProfile() {
        AppUser user = currentUser();
        return UserDetailsGetDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

    public void updateUserProfile(ProfileUpdateDto dto) {
        AppUser user = currentUser();
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
    }

    public void changePassword(PasswordChangeDto dto) {
        AppUser user = currentUser();
        if (!passwordEncoder.matches(dto.oldPassword(), user.getPassword())) {
            throw new BusinessException("incorrect_old_password");
        }

        if (new Zxcvbn().measure(dto.newPassword()).getScore() < AppConstants.PASSWORD_STRENGTH) {
            throw new BusinessException("weak_password");
        }

        user.setPassword(passwordEncoder.encode(dto.newPassword()));
    }

    public AppUser currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((AppUser) authentication.getPrincipal()).getUsername();
        return lookUpActiveUserByEmail(username);
    }

    public Page<IntegrationUsersGetDto> getIntegrationUsers(@Valid PageAndSortCriteria pageAndSortCriteria, String search) {
        Pageable pageable = pageAndSortCriteria.build("id");
        return userRepository.getIntegrationUsers(pageable, UserType.INTEGRATION, search);
    }

    public void activateUser(Long id) {
        AppUser appUser = userRepository.findById(id).orElseThrow(SecurityViolationException::new);
        appUser.setStatus(UserStatus.ACTIVE);
    }
}
