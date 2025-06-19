package com.tsira.notifications.Reporting.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Tag(name = "Reporting", description = "Operations related to managing Reports.")
public interface ReportControllerApi {

    @Operation(
            summary = "Get Total Customers",
            description = "Get total customers statistic. Required role: `ADMIN`."
    )
    ResponseEntity<Long> getTotalCustomers();

    @Operation(
            summary = "Get Customers With No OptIn",
            description = "Get customers with no optIn statistic: `ADMIN`."
    )
    ResponseEntity<Long> getCustomersWithNoOptIn();

    @Operation(
            summary = "Get Customers With OptIn",
            description = "Get customers with optIn statistic: `ADMIN`."
    )
    ResponseEntity<Long> getCustomersWithOptIn();

    @Operation(
            summary = "Get Total Notifications Sent",
            description = "Get total notifications sent statistic. Required role: `ADMIN`."
    )
    ResponseEntity<Long> getTotalNotificationsSent();

    @Operation(
            summary = "Get Failures By Reason",
            description = "Get failures by reason statistic. Required role: `ADMIN`."
    )
    ResponseEntity<Long> getFailuresByReason();

    @Operation(
            summary = "Get Opted In Count By Type",
            description = "Get Opted In Count By Notification Types. Required role: `Admin`"
    )
    ResponseEntity<Map<String, Long>> getOptedInByType();

}
