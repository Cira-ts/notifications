package com.tsira.notifications.address.service;


import com.tsira.notifications.address.controller.dto.AddressCreateDto;
import com.tsira.notifications.address.controller.dto.AddressGetDto;
import com.tsira.notifications.address.controller.dto.AddressUpdateDto;
import com.tsira.notifications.address.repository.AddressRepository;
import com.tsira.notifications.address.repository.entity.Address;
import com.tsira.notifications.address.repository.enums.AddressType;
import com.tsira.notifications.common.paginationandsort.PageAndSortCriteria;
import com.tsira.notifications.customer.repository.entity.Customer;
import com.tsira.notifications.customer.service.CustomerService;
import com.tsira.notifications.exception.SecurityViolationException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressServiceImp implements AddressService {
    private final AddressRepository repository;
    private final CustomerService customerService;

    public Page<AddressGetDto> getAddresses(PageAndSortCriteria pageAndSortCriteria,
                                            AddressType addressType,
                                            LocalDate validTillFrom,
                                            LocalDate validTillTo,
                                            String search) {
        Pageable pageable = pageAndSortCriteria.build("id");
        return repository.getAddresses(pageable, addressType, validTillFrom, validTillTo, search);
    }

    public void createAddress(AddressCreateDto dto) {
        Customer customer = customerService.lookUpCustomer(dto.customerId());
        Address address = Address.builder()
                .type(dto.type())
                .value(dto.value())
                .validTillDate(dto.validTillDate())
                .customer(customer)
                .build();
        repository.save(address);
    }

    public void updateAddress(Long id, AddressUpdateDto dto) {
        Address address = lookUpAddress(id);
        address.setType(dto.type());
        address.setValue(dto.value());
        address.setValidTillDate(dto.validTillDate());
    }

    public void deleteAddress(Long id) {
        Address address = lookUpAddress(id);
        repository.delete(address);
    }

    private Address lookUpAddress(Long id) {
        return repository.findById(id).orElseThrow(SecurityViolationException::new);
    }
}
