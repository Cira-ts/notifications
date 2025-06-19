package com.tsira.notifications.Integration.controller;

import com.tsira.notifications.Integration.controller.dto.CustomerUpdateDto;
import com.tsira.notifications.Integration.service.CustomerIntegrationService;
import com.tsira.notifications.address.controller.dto.AddressGetDto;
import com.tsira.notifications.address.repository.enums.AddressType;
import com.tsira.notifications.common.paginationandsort.PageAndSortCriteria;
import com.tsira.notifications.common.paginationandsort.PageView;
import com.tsira.notifications.notificationpreference.controller.dto.PreferenceGetDto;
import com.tsira.notifications.notificationpreference.repository.enums.NotificationType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/integration")
@RequiredArgsConstructor
public class CustomerIntegrationController implements CustomerIntegrationControllerApi{

    private final CustomerIntegrationService service;

    @Override
    @GetMapping("preferences")
    @PreAuthorize("hasRole('USER')")
    public PageView<PreferenceGetDto> getPreferences(
            @Valid PageAndSortCriteria pageAndSortCriteria,
            @RequestParam(required = false) NotificationType notificationType,
            @RequestParam(required = false) Boolean optedIn,
            @RequestParam(required = false) String search ) {
        return PageView.of(service.getPreferences(pageAndSortCriteria, notificationType, optedIn, search));
    }

    @Override
    @GetMapping("addresses")
    @PreAuthorize("hasRole('USER')")
    public PageView<AddressGetDto> getAddresses(
            @Valid PageAndSortCriteria pageAndSortCriteria,
            @RequestParam(required = false) AddressType addressType,
            @RequestParam(required = false) LocalDate validTillFrom,
            @RequestParam(required = false) LocalDate validTillTo,
            @RequestParam(required = false) String search ) {
        return PageView.of(service.getAddresses(pageAndSortCriteria, addressType, validTillFrom, validTillTo, search));
    }

    @Override
    @PutMapping("/customers/batch")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> batchUpdateCustomers(@RequestBody List<CustomerUpdateDto> customers) {
        service.batchUpdate(customers);
        return ResponseEntity.ok().build();
    }
}
