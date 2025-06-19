package com.tsira.notifications.address.controller.dto;

import com.tsira.notifications.address.repository.enums.AddressType;

import java.time.LocalDate;

public interface AddressGetDto {
    Long getId();

    AddressType getType();

    String getValue();

    LocalDate getValidTillDate();

    String getCustomerName();

    String getEmail();

    String getPhoneNumber();
}
