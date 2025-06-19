package com.tsira.notifications.Integration.register;

import com.tsira.notifications.security.register.controller.dto.RegisterUserRequestDto;
import com.tsira.notifications.security.register.service.UserRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("integration-user/registration")
@RequiredArgsConstructor
public class IntegrationUserRegistrationController {
    private final UserRegistrationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid RegisterUserRequestDto request) {
        service.registerIntegrationUser(request);
    }
}
