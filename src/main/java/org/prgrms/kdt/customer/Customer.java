package org.prgrms.kdt.customer;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Customer {
    private UUID id;
    private String customerId;
    private String email;

    public Customer(UUID id, String customerId, String email) {
        this.id = id;
        this.customerId = customerId;
        this.email = email;
    }

    @Override
    public String toString() {
        return "id: " + id + ", customerId: " + customerId + ", email: " + email + "\n";
    }
}
