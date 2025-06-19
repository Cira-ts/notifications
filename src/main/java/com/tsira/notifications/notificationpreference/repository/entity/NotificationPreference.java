package com.tsira.notifications.notificationpreference.repository.entity;

import com.tsira.notifications.customer.repository.entity.Customer;
import com.tsira.notifications.notificationpreference.repository.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification_preference")
public class NotificationPreference {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_notification_preference")
    @SequenceGenerator(name = "seq_notification_preference", sequenceName = "seq_notification_preference", allocationSize = 1, initialValue = 1000)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private NotificationType type;

    @Column(name = "opted_in")
    private boolean optedIn;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotificationPreference notificationPreference)) return false;

        return this.getId() != null && this.getId().equals(notificationPreference.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
