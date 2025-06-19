package com.tsira.notifications.notificationpreference.service;

import com.tsira.notifications.common.paginationandsort.PageAndSortCriteria;
import com.tsira.notifications.notificationpreference.controller.dto.PreferenceCreateDto;
import com.tsira.notifications.notificationpreference.controller.dto.PreferenceGetDto;
import com.tsira.notifications.notificationpreference.controller.dto.PreferenceUpdateDto;
import com.tsira.notifications.notificationpreference.repository.enums.NotificationType;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

public interface NotificationPreferenceService {
    Page<PreferenceGetDto> getPreferences(PageAndSortCriteria pageAndSortCriteria,
                                          NotificationType notificationType,
                                          Boolean optedIn,
                                          String search);

    void createPreference(PreferenceCreateDto dto);

    void updatePreference(Long id, @Valid PreferenceUpdateDto dto);

    void deletePreference(Long id);
}
