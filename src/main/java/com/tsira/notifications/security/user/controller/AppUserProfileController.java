package com.tsira.notifications.security.user.controller;

import com.tsira.notifications.security.user.controller.dto.PasswordChangeDto;
import com.tsira.notifications.security.user.controller.dto.ProfileUpdateDto;
import com.tsira.notifications.security.user.controller.dto.UserDetailsGetDTO;
import com.tsira.notifications.security.user.service.AdminUserService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin-profile")
@RequiredArgsConstructor
public class AppUserProfileController {

    private final AdminUserService service;

    @GetMapping
    @PermitAll
    public UserDetailsGetDTO getUserProfile() {
        return service.getProfile();
    }

    @PutMapping
    @PermitAll
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserProfile(@RequestBody @Valid ProfileUpdateDto dto) {
        service.updateUserProfile(dto);
    }

    @PatchMapping("change-password")
    @PermitAll
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@RequestBody @Valid PasswordChangeDto dto) {
        service.changePassword(dto);
    }
}
