package com.tsira.notifications.notification.repository;

import com.tsira.notifications.notification.controller.dto.NotificationLogGetDto;
import com.tsira.notifications.notification.repository.entity.NotificationLog;
import com.tsira.notifications.notification.repository.enums.NotificationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface NotificationLogRepository extends JpaRepository<NotificationLog, Long> {

    @Query("""
            SELECT
                a.id                AS id,
                a.type              AS type,
                a.status            AS status,
                a.sentTime          AS sentTime,
                a.error             AS error,
                ca.name             AS customerName,
                ca.email            AS email,
                ca.phoneNumber      AS phoneNumber
            FROM NotificationLog a
            JOIN a.customer ca
            WHERE (:notificationStatus is null or a.type = :notificationStatus)
                  AND (CAST(:sentTimeFrom as timestamp) IS NULL OR a.sentTime >=:sentTimeFrom)
                  AND (CAST(:sentTimeTo as timestamp) IS NULL OR a.sentTime <=:sentTimeTo)
                  AND (:search is null
                     or ca.name ilike %:search%
                     or ca.email ilike %:search%
                     or ca.phoneNumber ilike %:search%)
            """)
    Page<NotificationLogGetDto> getNotificationLogs(Pageable pageable,
                                                    NotificationStatus notificationStatus,
                                                    LocalDateTime sentTimeFrom,
                                                    LocalDateTime sentTimeTo,
                                                    String search);

    @Query("""
        SELECT COUNT(nl)
        FROM NotificationLog nl
        WHERE nl.status = 'FAILED'
    """)
    Long countFailuresByReason();

    @Query("""
        SELECT l.type,
               SUM(CASE WHEN l.status = 'DELIVERED' THEN 1 ELSE 0 END),
               COUNT(l)
        FROM NotificationLog l
        GROUP BY l.type
    """)
    List<Object[]> countDeliveryStatsByType();
}

