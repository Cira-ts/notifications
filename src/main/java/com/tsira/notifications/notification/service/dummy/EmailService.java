package com.tsira.notifications.notification.service.dummy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailService {
    public String send(String to, String subject, String body) {
        return UUID.randomUUID().toString();
    }
}
