package com.tsira.notifications.Reporting.controller;

import com.tsira.notifications.Reporting.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("admin/reports")
@RequiredArgsConstructor
public class ReportController implements ReportControllerApi {

    private final ReportService service;

    @Override
    @GetMapping("customers/count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getTotalCustomers() {
        long count = service.getTotalCustomers();
        return ResponseEntity.ok(count);
    }

    @Override
    @GetMapping("customers/no-optin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getCustomersWithNoOptIn() {
        long count = service.getCustomersWithNoOptIn();
        return ResponseEntity.ok(count);
    }

    @Override
    @GetMapping("customers/optin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getCustomersWithOptIn() {
        long count = service.getCustomersWithOptIn();
        return ResponseEntity.ok(count);
    }

    @Override
    @GetMapping("notifications/total-sent")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getTotalNotificationsSent() {
        long total = service.getTotalNotificationsSent();
        return ResponseEntity.ok(total);
    }

    @Override
    @GetMapping("notifications/failures/by-reason")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getFailuresByReason() {
        Long failures= service.getFailureCountByReason();
        return ResponseEntity.ok(failures);
    }

    @Override
    @GetMapping("preferences/opt-in/by-type")
    public ResponseEntity<Map<String, Long>> getOptedInByType() {
        Map<String, Long> optedInCounts = service.getOptedInByTypes();
        return ResponseEntity.ok(optedInCounts);
    }
}
