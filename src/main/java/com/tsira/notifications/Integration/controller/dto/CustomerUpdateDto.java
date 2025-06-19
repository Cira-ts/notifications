package com.tsira.notifications.Integration.controller.dto;

import com.tsira.notifications.address.controller.dto.AddressUpdateDto;
import com.tsira.notifications.notificationpreference.controller.dto.PreferenceUpdateDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CustomerUpdateDto(
        @NotNull
        Long customerId,

        @NotEmpty
        String name,

        @NotEmpty
        String email,

        @NotEmpty
        String phoneNumber,

        @Valid
        List<@NotNull PreferenceUpdateDto> preferences,

        @Valid
        List<@NotNull AddressUpdateDto> addresses
) {

}
