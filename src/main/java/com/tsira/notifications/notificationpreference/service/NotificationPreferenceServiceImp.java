package com.tsira.notifications.notificationpreference.service;

import com.tsira.notifications.common.paginationandsort.PageAndSortCriteria;
import com.tsira.notifications.customer.repository.entity.Customer;
import com.tsira.notifications.customer.service.CustomerService;
import com.tsira.notifications.notificationpreference.controller.dto.PreferenceCreateDto;
import com.tsira.notifications.notificationpreference.controller.dto.PreferenceGetDto;
import com.tsira.notifications.notificationpreference.controller.dto.PreferenceUpdateDto;
import com.tsira.notifications.notificationpreference.repository.NotificationPreferenceRepository;
import com.tsira.notifications.notificationpreference.repository.entity.NotificationPreference;
import com.tsira.notifications.notificationpreference.repository.enums.NotificationType;
import com.tsira.notifications.exception.SecurityViolationException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        Optional<NotificationPreference> existingPreference = repository
                .findByCustomerIdAndType(dto.customerId(), dto.type());

        if (existingPreference.isPresent()) {
            NotificationPreference preference = existingPreference.get();
            preference.setOptedIn(dto.optedIn());
            repository.save(preference);
        }else {
            Customer customer = customerService.lookUpCustomer(dto.customerId());
            NotificationPreference preference = NotificationPreference.builder()
                    .type(dto.type())
                    .optedIn(dto.optedIn())
                    .customer(customer)
                    .build();
            repository.save(preference);
        }
    }

    public void updatePreference(Long id, @Valid PreferenceUpdateDto dto) {
        NotificationPreference notificationPreference = lookUpNotificationPreference(id);
        notificationPreference.setType(dto.type());
        notificationPreference.setOptedIn(dto.optedIn());
    }

    public void deletePreference(Long id) {
        NotificationPreference notificationPreference = lookUpNotificationPreference(id);
        repository.delete(notificationPreference);
    }

    private NotificationPreference lookUpNotificationPreference(Long id) {
        return repository.findById(id).orElseThrow(SecurityViolationException::new);
    }
}
