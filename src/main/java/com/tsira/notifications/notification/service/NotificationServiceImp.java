package com.tsira.notifications.notification.service;

import com.tsira.notifications.customer.repository.CustomerRepository;
import com.tsira.notifications.customer.repository.entity.Customer;
import com.tsira.notifications.notification.controller.dto.SendNotificationRequestDto;
import com.tsira.notifications.notification.repository.enums.NotificationStatus;
import com.tsira.notifications.notification.service.dummy.EmailService;
import com.tsira.notifications.exception.SecurityViolationException;
import com.tsira.notifications.notification.service.dummy.SmsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationServiceImp implements NotificationService{
    private final CustomerRepository customerRepository;
    private final NotificationLogService notificationLogServiceImp;
    private final EmailService emailService;
    private final SmsService smsService;

    @Override
    public void sendAndTrackNotification(SendNotificationRequestDto request) {
        Customer customer = lookUpCustomer(request.customerId());
        String messageId = "";
        try {
            messageId = switch (request.addressType()) {
                case EMAIL -> emailService.send(customer.getEmail(), request.subject(), request.messageBody());
                case SMS -> smsService.send(customer.getPhoneNumber(), request.messageBody());
                default -> throw new UnsupportedOperationException("Unsupported type");
            };

            notificationLogServiceImp.logNotification(
                    customer,
                    request.type(),
                    NotificationStatus.DELIVERED,
                    null,
                    messageId
            );
        } catch (Exception e) {
            notificationLogServiceImp.logNotification(
                    customer,
                    request.type(),
                    NotificationStatus.FAILED,
                    e.getMessage(),
                    messageId
            );
        }
    }

    private Customer lookUpCustomer(Long id) {
        return customerRepository.findById(id).orElseThrow(SecurityViolationException::new);
    }
}
