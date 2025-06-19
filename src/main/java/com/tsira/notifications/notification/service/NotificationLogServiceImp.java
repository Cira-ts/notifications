package com.tsira.notifications.notification.service;

import com.tsira.notifications.common.paginationandsort.PageAndSortCriteria;
import com.tsira.notifications.customer.repository.entity.Customer;
import com.tsira.notifications.notification.controller.dto.NotificationLogGetDto;
import com.tsira.notifications.notification.repository.NotificationLogRepository;
import com.tsira.notifications.notification.repository.entity.NotificationLog;
import com.tsira.notifications.notification.repository.enums.NotificationStatus;
import com.tsira.notifications.notificationpreference.repository.enums.NotificationType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationLogServiceImp implements NotificationLogService {
    private final NotificationLogRepository repository;

    @Override
    public void logNotification(Customer customer,
                                NotificationType type,
                                NotificationStatus status,
                                String error,
                                String messageUuid) {

        NotificationLog log = NotificationLog.builder()
                .customer(customer)
                .type(type)
                .status(status)
                .sentTime(LocalDateTime.now())
                .error(error)
                .messageUuid(messageUuid)
                .build();

        repository.save(log);
    }

    @Override
    public Page<NotificationLogGetDto> getNotificationLogs(PageAndSortCriteria pageAndSortCriteria,
                                                           NotificationStatus notificationStatus,
                                                           LocalDateTime sentTimeFrom,
                                                           LocalDateTime sentTimeTo,
                                                           String search) {
        Pageable pageable = pageAndSortCriteria.build("id");
        return repository.getNotificationLogs(pageable, notificationStatus, sentTimeFrom, sentTimeTo, search);
    }
}
