package com.tsira.notifications.customer.controller;

import com.tsira.notifications.common.paginationandsort.PageAndSortCriteria;
import com.tsira.notifications.common.paginationandsort.PageView;
import com.tsira.notifications.customer.controller.dto.CustomerCreateUpdateDto;
import com.tsira.notifications.customer.controller.dto.CustomerDetailsGetDto;
import com.tsira.notifications.customer.controller.dto.CustomerGetDto;
import com.tsira.notifications.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/customers")
@RequiredArgsConstructor
public class CustomerController implements CustomerControllerApi {

    private final CustomerService service;

    @Override
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public PageView<CustomerGetDto> getCustomers(
            @Valid PageAndSortCriteria pageAndSortCriteria,
            @RequestParam(required = false) String search) {
        return PageView.of(service.getCustomers(pageAndSortCriteria, search));
    }

    @Override
    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CustomerDetailsGetDto getCustomerDetails(@PathVariable Long id) {
        return service.getCustomerDetails(id);
    }

    @Override
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCustomer(@RequestBody @Valid CustomerCreateUpdateDto dto) {
        service.createCustomer(dto);
    }

    @Override
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomers(@PathVariable Long id, @RequestBody @Valid CustomerCreateUpdateDto dto) {
        service.updateCustomers(id, dto);
    }

    @Override
    @PatchMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomers(@PathVariable Long id) {
        service.deleteCustomers(id);
    }
}
