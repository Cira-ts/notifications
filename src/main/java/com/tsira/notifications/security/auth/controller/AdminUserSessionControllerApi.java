package com.tsira.notifications.security.auth.controller;

import com.tsira.notifications.security.auth.controller.dto.AuthenticationRequestDto;
import com.tsira.notifications.security.auth.controller.dto.AuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Tag(name = "Admin User Session", description = "Operations related to managing Admin User Session.")
public interface AdminUserSessionControllerApi {

    @Operation(summary = "Authenticate")
    ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequestDto request);

    @Operation(summary = "RefreshToken")
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
