package com.tsira.notifications.customer.controller.dto;


import com.tsira.notifications.customer.repository.entity.Customer;
import com.tsira.notifications.notificationpreference.repository.entity.NotificationPreference;

import java.util.List;

public record CustomerDetailsGetDto (
        Long id,
        String getName,
        String getEmail,
        String getPhoneNumber,
        List<NotificationPreference> preferences
) {
    public static CustomerDetailsGetDto fromEntity(Customer customer) {
        return new CustomerDetailsGetDto(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getPreferences()
        );
    }
}
