package com.tsira.notifications.customer.service;

import com.tsira.notifications.common.paginationandsort.PageAndSortCriteria;
import com.tsira.notifications.customer.controller.dto.CustomerCreateUpdateDto;
import com.tsira.notifications.customer.controller.dto.CustomerDetailsGetDto;
import com.tsira.notifications.customer.controller.dto.CustomerGetDto;
import com.tsira.notifications.customer.repository.entity.Customer;
import org.springframework.data.domain.Page;

public interface CustomerService {
    Page<CustomerGetDto> getCustomers(PageAndSortCriteria pageAndSortCriteria, String search);

    CustomerDetailsGetDto getCustomerDetails(Long id);

    void createCustomer(CustomerCreateUpdateDto dto);

    void updateCustomers(Long id, CustomerCreateUpdateDto dto);

    void deleteCustomers(Long id);

    Customer lookUpCustomer(Long id);
}
