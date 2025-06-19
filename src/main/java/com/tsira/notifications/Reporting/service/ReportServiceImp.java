package com.tsira.notifications.Reporting.service;

import com.tsira.notifications.customer.repository.CustomerRepository;
import com.tsira.notifications.notification.repository.NotificationLogRepository;
import com.tsira.notifications.notificationpreference.repository.NotificationPreferenceRepository;
import com.tsira.notifications.notificationpreference.repository.enums.NotificationType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportServiceImp implements ReportService {
    private final CustomerRepository customerRepository;
    private final NotificationLogRepository notificationLogRepository;
    private final NotificationPreferenceRepository notificationPreferenceRepository;

    @Override
    public long getTotalCustomers() {
        return customerRepository.count();
    }

    @Override
    public long getCustomersWithNoOptIn() {
        return customerRepository.countCustomersWithNoOptIn();
    }

    @Override
    public long getCustomersWithOptIn() {
        return customerRepository.countCustomersWithOptIn();
    }

    @Override
    public long getTotalNotificationsSent() {
        return notificationLogRepository.count();
    }

    @Override
    public long getFailureCountByReason() {
        return notificationLogRepository.countFailuresByReason();
    }

    @Override
    public Map<String, Long> getOptedInByTypes() {
        List<Object[]> results = notificationPreferenceRepository.getOptedInByType();
        Map<String, Long> resultMap = new HashMap<>();
        for (Object[] row : results) {
            NotificationType type = (NotificationType) row[0];
            Long count = (Long) row[1];
            resultMap.put(type.name(), count);
        }
        return resultMap;
    }

}
