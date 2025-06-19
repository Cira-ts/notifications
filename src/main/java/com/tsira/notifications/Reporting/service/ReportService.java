package com.tsira.notifications.Reporting.service;

import java.util.Map;

public interface ReportService {

    long getTotalCustomers();

    long getCustomersWithNoOptIn();

    long getCustomersWithOptIn();

    long getTotalNotificationsSent();

    long getFailureCountByReason();

    Map<String, Long> getOptedInByTypes();
}
