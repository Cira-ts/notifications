package com.tsira.notifications.Integration.service;

import com.tsira.notifications.Integration.controller.dto.CustomerUpdateDto;
import com.tsira.notifications.address.controller.dto.AddressGetDto;
import com.tsira.notifications.address.repository.enums.AddressType;
import com.tsira.notifications.common.paginationandsort.PageAndSortCriteria;
import com.tsira.notifications.notificationpreference.controller.dto.PreferenceGetDto;
import com.tsira.notifications.notificationpreference.repository.enums.NotificationType;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface CustomerIntegrationService {
    Page<AddressGetDto> getAddresses(PageAndSortCriteria pageAndSortCriteria,
                                     AddressType addressType,
                                     LocalDate validTillFrom,
                                     LocalDate validTillTo,
                                     String search);

    Page<PreferenceGetDto> getPreferences(PageAndSortCriteria pageAndSortCriteria,
                                          NotificationType notificationType,
                                          Boolean optedIn,
                                          String search);

    void batchUpdate(List<CustomerUpdateDto> customers);
}
