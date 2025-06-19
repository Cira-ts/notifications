package com.tsira.notifications.address.repository.entity;

import com.tsira.notifications.address.repository.enums.AddressType;
import com.tsira.notifications.customer.repository.entity.Customer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
public class Address {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_address")
    @SequenceGenerator(name = "seq_address", sequenceName = "seq_address", allocationSize = 1, initialValue = 1000)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private AddressType type;

    @Column(name = "value")
    private String value;

    @Column(name = "valid_till_date")
    private LocalDate validTillDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address address)) return false;

        return this.getId() != null && this.getId().equals(address.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
