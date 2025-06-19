package com.tsira.notifications.notificationpreference.controller;

import com.tsira.notifications.common.paginationandsort.PageAndSortCriteria;
import com.tsira.notifications.common.paginationandsort.PageView;
import com.tsira.notifications.notificationpreference.controller.dto.PreferenceCreateDto;
import com.tsira.notifications.notificationpreference.controller.dto.PreferenceGetDto;
import com.tsira.notifications.notificationpreference.controller.dto.PreferenceUpdateDto;
import com.tsira.notifications.notificationpreference.repository.enums.NotificationType;
import com.tsira.notifications.notificationpreference.service.NotificationPreferenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("admin/preferences")
@RequiredArgsConstructor
public class NotificationPreferenceController implements NotificationPreferenceControllerApi{

    private final NotificationPreferenceService service;

    @Override
    @GetMapping("types")
    @PreAuthorize("hasRole('ADMIN')")
    public NotificationType[] getNotificationTypes() {
        return NotificationType.values();
    }

    @Override
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public PageView<PreferenceGetDto> getPreferences(
            @Valid PageAndSortCriteria pageAndSortCriteria,
            @RequestParam(required = false) NotificationType notificationType,
            @RequestParam(required = false) Boolean optedIn,
            @RequestParam(required = false) String search ) {
        return PageView.of(service.getPreferences(pageAndSortCriteria, notificationType, optedIn, search));
    }

    @Override
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPreference(@RequestBody @Valid PreferenceCreateDto dto) {
        service.createPreference(dto);
    }

    @Override
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePreference(@PathVariable Long id, @RequestBody @Valid PreferenceUpdateDto dto) {
        service.updatePreference(id, dto);
    }

    @Override
    @PatchMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePreference(@PathVariable Long id) {
        service.deletePreference(id);
    }
}
