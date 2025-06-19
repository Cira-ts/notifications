package com.tsira.notifications.address.controller;

import com.tsira.notifications.address.controller.dto.AddressCreateDto;
import com.tsira.notifications.address.controller.dto.AddressGetDto;
import com.tsira.notifications.address.controller.dto.AddressUpdateDto;
import com.tsira.notifications.address.repository.enums.AddressType;
import com.tsira.notifications.address.service.AddressService;
import com.tsira.notifications.common.paginationandsort.PageView;
import com.tsira.notifications.common.paginationandsort.PageAndSortCriteria;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("admin/addresses")
@RequiredArgsConstructor
public class AddressController implements AddressControllerApi {

    private final AddressService service;

    @Override
    @GetMapping("types")
    @PreAuthorize("hasRole('ADMIN')")
    public AddressType[] getAddressTypes() {
        return AddressType.values();
    }

    @Override
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public PageView<AddressGetDto> getAddresses(
            @Valid PageAndSortCriteria pageAndSortCriteria,
            @RequestParam(required = false) AddressType addressType,
            @RequestParam(required = false) LocalDate validTillFrom,
            @RequestParam(required = false) LocalDate validTillTo,
            @RequestParam(required = false) String search ) {
        return PageView.of(service.getAddresses(pageAndSortCriteria, addressType, validTillFrom, validTillTo, search));
    }

    @Override
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAddress(@RequestBody @Valid AddressCreateDto dto) {
        service.createAddress(dto);
    }

    @Override
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAddress(@PathVariable Long id, @RequestBody @Valid AddressUpdateDto dto) {
        service.updateAddress(id, dto);
    }

    @Override
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddress(@PathVariable Long id) {
        service.deleteAddress(id);
    }
}
