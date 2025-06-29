package com.tsira.notifications.exception;

import com.tsira.notifications.security.token.repository.AdminUserTokenRepository;
import com.tsira.notifications.security.token.repository.entity.AdminUserToken;
import com.tsira.notifications.security.user.repository.AdminUserRepository;
import com.tsira.notifications.security.user.repository.entity.AppUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Map;

@ControllerAdvice
@AllArgsConstructor
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private final AdminUserTokenRepository tokenRepository;
    private final AdminUserRepository appUserRepository;

    @ExceptionHandler(value = SecurityViolationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Object> handleSecurityViolation(RuntimeException ex, WebRequest request) {
        logout();
        return handleExceptionInternal(ex, null, HttpHeaders.EMPTY, HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<Object> handleBusiness(RuntimeException ex) {
        Map<String, String> body = Map.of("errorCode", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    private void logout() {
        AppUser user = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();
        AppUser byEmail = appUserRepository.findByEmail(username);
        List<AdminUserToken> allValidTokenByUsername = tokenRepository.findAllValidTokenByUser(byEmail.getId());
        if (allValidTokenByUsername != null && !allValidTokenByUsername.isEmpty()) {
            tokenRepository.deleteAll(allValidTokenByUsername);
        }
        SecurityContextHolder.clearContext();
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<Object> handleBadRequest(RuntimeException ex, WebRequest request) {
        String body = null;
        if (ex.getMessage() != null) {
            body = new ObjectMapper().createObjectNode().put("errorCode", ex.getMessage()).toString();
        }
        return handleExceptionInternal(ex, body, HttpHeaders.EMPTY, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleUnauthorized(RuntimeException ex, WebRequest request) {
        logout();
        return handleExceptionInternal(ex, null, HttpHeaders.EMPTY, HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(value = OptimisticLockingFailureException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleOptimisticLockException(OptimisticLockingFailureException ex, WebRequest request) {
        Map<String, String> body = Map.of("errorCode", "OPTIMISTIC_LOCK_ERROR",
                "message", "The resource was updated by another admin. Please reload and try again.");
        return handleExceptionInternal(ex, body, HttpHeaders.EMPTY, HttpStatus.CONFLICT, request);
    }
}
