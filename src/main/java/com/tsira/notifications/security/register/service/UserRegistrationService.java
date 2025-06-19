package com.tsira.notifications.security.register.service;

import com.tsira.notifications.common.AppConstants;
import com.tsira.notifications.exception.BusinessException;
import com.tsira.notifications.exception.ExceptionUtil;
import com.tsira.notifications.security.auth.service.AuthenticationService;
import com.tsira.notifications.security.register.controller.dto.RegisterUserRequestDto;
import com.tsira.notifications.security.service.JwtService;
import com.tsira.notifications.security.user.repository.AdminUserRepository;
import com.tsira.notifications.security.user.repository.entity.AppUser;
import com.tsira.notifications.security.user.repository.enums.Role;
import com.tsira.notifications.security.user.repository.enums.UserStatus;
import com.tsira.notifications.security.user.repository.enums.UserType;
import com.nulabinc.zxcvbn.Zxcvbn;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class UserRegistrationService {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AdminUserRepository repository;

    public String registerAdminUser(RegisterUserRequestDto request) {
        return registerUser(request, UserType.ADMIN, Role.ADMIN, UserStatus.ACTIVE);
    }

    public String registerIntegrationUser(RegisterUserRequestDto request) {
        return registerUser(request, UserType.INTEGRATION, Role.USER, UserStatus.DISABLED);
    }

    private String registerUser(RegisterUserRequestDto request, UserType userType, Role role, UserStatus userStatus) {
        if (new Zxcvbn().measure(request.password()).getScore() < AppConstants.PASSWORD_STRENGTH) {
            throw new BusinessException("weak_password");
        }

        AppUser user = AppUser.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .status(userStatus)
                .userType(userType)
                .registrationDate(LocalDateTime.now())
                .role(role)
                .build();

        return saveUserAndGenerateResponse(user);
    }

    private String saveUserAndGenerateResponse(AppUser user) {
        try {
            repository.saveAndFlush(user);
        } catch (Exception e) {
            ExceptionUtil.handleDatabaseExceptions(e, Map.of("unique_email", "email_already_exists"));
        }
        String jwtToken = jwtService.generateToken(user);
        authenticationService.saveUserToken(user, jwtToken);
        return jwtToken;
    }
}
