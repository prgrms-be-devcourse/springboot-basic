package org.prgrms.voucherapp.engine.customer.entity;

import lombok.Getter;
import org.prgrms.voucherapp.global.enums.VoucherType;

import java.text.MessageFormat;
import java.util.UUID;

@Getter
public class Customer {
    private final UUID customerId;
    private String name;
    private final String email;
    private String status=null;

    public Customer(UUID customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

    public Customer(UUID customerId, String name, String email, String status) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("ID : %36s, STATUS : %-10s, NAME : %-20s, EMAIL : %-50s", customerId, status, name, email);
    }
}
