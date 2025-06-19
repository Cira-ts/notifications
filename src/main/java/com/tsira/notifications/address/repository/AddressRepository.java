package com.tsira.notifications.address.repository;

import com.tsira.notifications.address.repository.entity.Address;
import com.tsira.notifications.address.controller.dto.AddressGetDto;
import com.tsira.notifications.address.repository.enums.AddressType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("""
            SELECT
                a.id                AS id,
                a.type              AS type,
                a.value             AS value,
                a.validTillDate     AS validTillDate,
                ca.name             AS customerName,
                ca.email            AS email,
                ca.phoneNumber      AS phoneNumber
            FROM Address a
            JOIN a.customer ca
            WHERE (:addressType is null or a.type = :addressType)
                  AND (CAST(:validTillFrom as date) IS NULL OR a.validTillDate >=:validTillFrom)
                  AND (CAST(:validTillTo as date) IS NULL OR a.validTillDate <=:validTillTo)
                  AND (:search is null
                     or a.value ilike %:search%
                     or ca.name ilike %:search%
                     or ca.email ilike %:search%
                     or ca.phoneNumber ilike %:search%)
            """)
    Page<AddressGetDto> getAddresses(Pageable pageable,
                                     AddressType addressType,
                                     LocalDate validTillFrom,
                                     LocalDate validTillTo,
                                     String search);

    @Modifying
    @Query("DELETE FROM Address a WHERE a.validTillDate < :today")
    void deleteOutdatedAddresses(LocalDate today);

    @Modifying
    @Query("DELETE FROM Address a WHERE a.customer.id IN :customerIds")
    void deleteAllByCustomerIdIn(List<Long> customerIds);
}
