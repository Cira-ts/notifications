package com.tsira.notifications.notification.repository.entity;

import com.tsira.notifications.customer.repository.entity.Customer;
import com.tsira.notifications.notification.repository.enums.NotificationStatus;
import com.tsira.notifications.notificationpreference.repository.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification_log")
public class NotificationLog {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_notification_log")
    @SequenceGenerator(name = "seq_notification_log", sequenceName = "seq_notification_log", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private NotificationStatus status;

    @Column(name = "sent_time")
    private LocalDateTime sentTime;

    @Column(name = "error")
    private String error;

    @Column(name = "message_uuid")
    private String messageUuid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotificationLog notificationLog)) return false;

        return this.getId() != null && this.getId().equals(notificationLog.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}

