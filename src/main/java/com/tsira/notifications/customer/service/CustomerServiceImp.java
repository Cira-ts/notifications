package com.tsira.notifications.customer.service;

import com.tsira.notifications.common.paginationandsort.PageAndSortCriteria;
import com.tsira.notifications.customer.controller.dto.CustomerCreateUpdateDto;
import com.tsira.notifications.customer.controller.dto.CustomerDetailsGetDto;
import com.tsira.notifications.customer.controller.dto.CustomerGetDto;
import com.tsira.notifications.customer.repository.CustomerRepository;
import com.tsira.notifications.customer.repository.entity.Customer;
import com.tsira.notifications.exception.BusinessException;
import com.tsira.notifications.exception.SecurityViolationException;
import com.tsira.notifications.exception.ExceptionUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceImp implements CustomerService {
    
    private final CustomerRepository repository;

    public Page<CustomerGetDto> getCustomers(PageAndSortCriteria pageAndSortCriteria, String search) {
        Pageable pageable = pageAndSortCriteria.build("id");
        return repository.getCustomers(pageable, search);
    }

    public CustomerDetailsGetDto getCustomerDetails(Long id) {
        Customer customer = repository.getCustomerDetailsById(id);
        return CustomerDetailsGetDto.fromEntity(customer);
    }

    public void createCustomer(CustomerCreateUpdateDto dto) {
        Customer customer = Customer.builder()
                .name(dto.name())
                .email(dto.email())
                .phoneNumber(dto.phoneNumber())
                .build();
        try {
            repository.saveAndFlush(customer);
        } catch (Exception e) {
            ExceptionUtil.handleDatabaseExceptions(e, Map.of("unique_customer_email", "customer email already exists"));
        }
    }

    public void updateCustomers(Long id, CustomerCreateUpdateDto dto) {
        Customer customer = lookUpCustomer(id);
        customer.setName(dto.name());
        customer.setPhoneNumber(dto.phoneNumber());
        try {
            customer.setEmail(dto.email());
            repository.saveAndFlush(customer);
        } catch (Exception e) {
            ExceptionUtil.handleDatabaseExceptions(e, Map.of("unique_customer_email", "customer email already exists"));
        }
    }

    public void deleteCustomers(Long id) {
        Customer customer = lookUpCustomer(id);
        try {
            repository.delete(customer);
        } catch (DataIntegrityViolationException ex) {
            throw new BusinessException("cant_delete");
        }
    }

    public Customer lookUpCustomer(Long id) {
        return repository.findById(id).orElseThrow(SecurityViolationException::new);
    }
}
