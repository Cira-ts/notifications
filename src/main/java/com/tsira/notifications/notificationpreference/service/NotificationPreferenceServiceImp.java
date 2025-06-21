package com.tsira.notifications.notificationpreference.service;

import com.tsira.notifications.common.paginationandsort.PageAndSortCriteria;
import com.tsira.notifications.customer.repository.entity.Customer;
import com.tsira.notifications.customer.service.CustomerService;
import com.tsira.notifications.exception.ExceptionUtil;
import com.tsira.notifications.notificationpreference.controller.dto.PreferenceCreateDto;
import com.tsira.notifications.notificationpreference.controller.dto.PreferenceGetDto;
import com.tsira.notifications.notificationpreference.controller.dto.PreferenceUpdateDto;
import com.tsira.notifications.notificationpreference.repository.NotificationPreferenceRepository;
import com.tsira.notifications.notificationpreference.repository.entity.NotificationPreference;
import com.tsira.notifications.notificationpreference.repository.enums.NotificationType;
import com.tsira.notifications.exception.SecurityViolationException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationPreferenceServiceImp implements NotificationPreferenceService{
    private final CustomerService customerService;
    private final NotificationPreferenceRepository repository;

    public Page<PreferenceGetDto> getPreferences(PageAndSortCriteria pageAndSortCriteria,
                                                 NotificationType notificationType,
                                                 Boolean optedIn,
                                                 String search) {
        Pageable pageable = pageAndSortCriteria.build("id");
        return repository.getPreferences(pageable, notificationType, optedIn, search);
    }

    public void createPreference(PreferenceCreateDto dto) {
        Customer customer = customerService.lookUpCustomer(dto.customerId());
        NotificationPreference preference = NotificationPreference.builder()
                .type(dto.type())
                .optedIn(dto.optedIn())
                .customer(customer)
                .build();
        try {
            repository.saveAndFlush(preference);
        } catch (DataIntegrityViolationException e) {
            ExceptionUtil.handleDatabaseExceptions(e, Map.of("unique_notification_preference_type_customer", "preference_already_exists"));
        }
    }

    public void updatePreference(Long id, PreferenceUpdateDto dto) {
        NotificationPreference notificationPreference = lookUpNotificationPreference(id);
        notificationPreference.setType(dto.type());
        notificationPreference.setOptedIn(dto.optedIn());
        try {
            repository.saveAndFlush(notificationPreference);
        } catch (DataIntegrityViolationException e) {
            ExceptionUtil.handleDatabaseExceptions(e, Map.of("unique_notification_preference_type_customer", "preference_already_exists"));
        }
    }

    public void deletePreference(Long id) {
        NotificationPreference notificationPreference = lookUpNotificationPreference(id);
        repository.delete(notificationPreference);
    }

    private NotificationPreference lookUpNotificationPreference(Long id) {
        return repository.findById(id).orElseThrow(SecurityViolationException::new);
    }
}
