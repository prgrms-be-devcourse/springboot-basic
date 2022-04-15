package org.voucherProject.voucherProject.entity.customer;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Customer {

    private final UUID customerId;

    private final String customerName;

    private final String customerEmail;

    private String password;

    private List<UUID> vouchers = new ArrayList<>();

    public Customer(UUID customerId, String customerName, String customerEmail, String password) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.password = password;
    }
}
