package com.tsira.notifications.address.service;


import com.tsira.notifications.address.controller.dto.AddressCreateDto;
import com.tsira.notifications.address.controller.dto.AddressGetDto;
import com.tsira.notifications.address.controller.dto.AddressUpdateDto;
import com.tsira.notifications.address.repository.enums.AddressType;
import com.tsira.notifications.common.paginationandsort.PageAndSortCriteria;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface AddressService {

    Page<AddressGetDto> getAddresses(PageAndSortCriteria pageAndSortCriteria,
                                     AddressType addressType,
                                     LocalDate validTillFrom,
                                     LocalDate validTillTo,
                                     String search);

    void createAddress(AddressCreateDto dto);

    void updateAddress(Long id, AddressUpdateDto dto);

    void deleteAddress(Long id);
}
