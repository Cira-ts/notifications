package com.tsira.notifications.notification.service;

import com.tsira.notifications.common.paginationandsort.PageAndSortCriteria;
import com.tsira.notifications.customer.repository.entity.Customer;
import com.tsira.notifications.notification.controller.dto.NotificationLogGetDto;
import com.tsira.notifications.notification.repository.enums.NotificationStatus;
import com.tsira.notifications.notificationpreference.repository.enums.NotificationType;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public interface NotificationLogService {
    void logNotification(Customer customer, NotificationType type, NotificationStatus status, String error, String messageUuid);

    Page<NotificationLogGetDto> getNotificationLogs(PageAndSortCriteria pageAndSortCriteria,
                                                    NotificationStatus notificationStatus,
                                                    LocalDateTime sentTimeFrom,
                                                    LocalDateTime sentTimeTo,
                                                    String search);
}
