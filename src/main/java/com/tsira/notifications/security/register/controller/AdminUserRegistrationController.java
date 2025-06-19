package com.tsira.notifications.security.register.controller;

import com.tsira.notifications.security.register.controller.dto.RegisterUserRequestDto;
import com.tsira.notifications.security.register.service.UserRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("admin-user/registration")
@RequiredArgsConstructor
public class AdminUserRegistrationController {
    private final UserRegistrationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> register(@RequestBody @Valid RegisterUserRequestDto request) {
        return Map.of("access_token", service.registerAdminUser(request));
    }
}
