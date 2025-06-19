package com.tsira.notifications.customer.repository;

import com.tsira.notifications.common.IdNameDto;
import com.tsira.notifications.customer.controller.dto.CustomerGetDto;
import com.tsira.notifications.customer.repository.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("""
            SELECT
                c.id            AS id,
                c.name          AS name,
                c.email         AS email,
                c.phoneNumber   AS phoneNumber
            FROM Customer c
            WHERE (:search is null
                     or c.name ilike %:search%
                     or c.email ilike %:search%
                     or c.phoneNumber ilike %:search%)
            """)
    Page<CustomerGetDto> getCustomers(Pageable pageable, String search);

    @Query("""
            SELECT
                c
            FROM Customer c
            LEFT JOIN FETCH c.preferences cp
            WHERE c.id = :customerId
            """)
    Customer getCustomerDetailsById(Long customerId);

    @Query("SELECT c.id as id, c.name as name from Customer c")
    List<IdNameDto> findAllIdNames();

    @Query("""
            SELECT count(c)
            FROM Customer c
            WHERE c.preferences IS EMPTY
             OR NOT EXISTS (
             SELECT p from NotificationPreference p
             WHERE p.customer = c AND p.optedIn = true
             )
            """)
    long countCustomersWithNoOptIn();

    @Query("""
             SELECT COUNT(DISTINCT c)
             FROM Customer c
             JOIN c.preferences p
             WHERE p.optedIn = true
            """)
    long countCustomersWithOptIn();
}
