package com.tsira.notifications.notification.service.dummy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SmsService {
    public String send(String phoneNumber, String body) {
        return UUID.randomUUID().toString();
    }
}
