package com.tsira.notifications.notificationpreference.repository;

import com.tsira.notifications.notificationpreference.controller.dto.PreferenceGetDto;
import com.tsira.notifications.notificationpreference.repository.entity.NotificationPreference;
import com.tsira.notifications.notificationpreference.repository.enums.NotificationType;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface NotificationPreferenceRepository extends JpaRepository<NotificationPreference, Long> {

    @Query("""
            SELECT
                np.id                AS id,
                np.type              AS notificationType,
                np.optedIn           AS optedIn,
                cnp.name             AS customerName,
                cnp.email            AS email,
                cnp.phoneNumber      AS phoneNumber
            FROM NotificationPreference np
            JOIN np.customer cnp
            WHERE (:notificationType is null or np.type = :notificationType)
                  AND (:optedIn is null or np.optedIn = :optedIn)
                  AND (:search is null
                     or cnp.name ilike %:search%
                     or cnp.email ilike %:search%
                     or cnp.phoneNumber ilike %:search%)
            """)
    Page<PreferenceGetDto> getPreferences(Pageable pageable, NotificationType notificationType, Boolean optedIn, String search);

    @Modifying
    @Query("DELETE FROM NotificationPreference np WHERE np.customer.id IN :customerIds")
    void deleteAllByCustomerIdIn(List<Long> customerIds);

    Optional<NotificationPreference> findByCustomerIdAndType(Long customerId, NotificationType type);

    @Query("""
            SELECT np.type, count(np)
            FROM NotificationPreference np
            WHERE np.optedIn = true
            group by np.type
        """)
    List<Object[]> getOptedInByType();
}
