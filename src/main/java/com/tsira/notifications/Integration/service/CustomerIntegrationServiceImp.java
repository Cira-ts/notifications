package com.tsira.notifications.Integration.service;

import com.tsira.notifications.Integration.controller.dto.CustomerUpdateDto;
import com.tsira.notifications.address.controller.dto.AddressGetDto;
import com.tsira.notifications.address.repository.AddressRepository;
import com.tsira.notifications.address.repository.entity.Address;
import com.tsira.notifications.address.repository.enums.AddressType;
import com.tsira.notifications.common.paginationandsort.PageAndSortCriteria;
import com.tsira.notifications.customer.repository.CustomerRepository;
import com.tsira.notifications.customer.repository.entity.Customer;
import com.tsira.notifications.exception.BusinessException;
import com.tsira.notifications.notificationpreference.controller.dto.PreferenceGetDto;
import com.tsira.notifications.notificationpreference.repository.NotificationPreferenceRepository;
import com.tsira.notifications.notificationpreference.repository.entity.NotificationPreference;
import com.tsira.notifications.notificationpreference.repository.enums.NotificationType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerIntegrationServiceImp implements CustomerIntegrationService{

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final NotificationPreferenceRepository notificationPreferenceRepository;

    public Page<AddressGetDto> getAddresses(PageAndSortCriteria pageAndSortCriteria,
                                            AddressType addressType,
                                            LocalDate validTillFrom,
                                            LocalDate validTillTo,
                                            String search) {
        Pageable pageable = pageAndSortCriteria.build("id");
        return addressRepository.getAddresses(pageable, addressType, validTillFrom, validTillTo, search);
    }

    public Page<PreferenceGetDto> getPreferences(PageAndSortCriteria pageAndSortCriteria,
                                                 NotificationType notificationType,
                                                 Boolean optedIn,
                                                 String search) {
        Pageable pageable = pageAndSortCriteria.build("id");
        return notificationPreferenceRepository.getPreferences(pageable, notificationType, optedIn, search);
    }

    public void batchUpdate(List<CustomerUpdateDto> customers) {
        List<Long> ids = customers.stream()
                .map(CustomerUpdateDto::customerId)
                .toList();

        List<Customer> allCustomers = customerRepository.findAllById(ids);
        Map<Long, Customer> customerMap = allCustomers.stream()
                .collect(Collectors.toMap(Customer::getId, Function.identity()));

        if (customerMap.size() != ids.size()) {
            throw new BusinessException("Some customers not found");
        }

        notificationPreferenceRepository.deleteAllByCustomerIdIn(ids);
        addressRepository.deleteAllByCustomerIdIn(ids);

        List<NotificationPreference> newPreferences = new ArrayList<>();
        List<Address> newAddresses = new ArrayList<>();

        for (CustomerUpdateDto dto : customers) {
            Customer customer = customerMap.get(dto.customerId());
            customer.setName(dto.name());
            customer.setEmail(dto.email());
            customer.setPhoneNumber(dto.phoneNumber());

            newPreferences.addAll(dto.preferences().stream()
                    .map(pref -> NotificationPreference.builder()
                            .customer(customer)
                            .type(pref.type())
                            .optedIn(pref.optedIn())
                            .build())
                    .toList());

            newAddresses.addAll(dto.addresses().stream()
                    .map(addr -> Address.builder()
                            .customer(customer)
                            .type(addr.type())
                            .value(addr.value())
                            .validTillDate(addr.validTillDate())
                            .build())
                    .toList());
        }

        notificationPreferenceRepository.saveAll(newPreferences);
        addressRepository.saveAll(newAddresses);
        customerRepository.saveAll(allCustomers);
    }
}
