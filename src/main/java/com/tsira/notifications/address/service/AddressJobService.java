package com.tsira.notifications.address.service;

import com.tsira.notifications.address.repository.AddressRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressJobService {
    private final AddressRepository repository;

    @Scheduled(cron = "0 0 0 * * *")
    public void cleanOutDatedAddresses() {
        repository.deleteOutdatedAddresses(LocalDate.now());
    }
}
