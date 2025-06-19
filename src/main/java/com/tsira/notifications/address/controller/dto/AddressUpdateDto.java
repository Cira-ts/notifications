package com.tsira.notifications.address.controller.dto;

import com.tsira.notifications.address.repository.enums.AddressType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AddressUpdateDto(
        @NotNull
        AddressType type,

        @NotEmpty
        String value,

        @NotNull
        @FutureOrPresent
        LocalDate validTillDate
) {
}