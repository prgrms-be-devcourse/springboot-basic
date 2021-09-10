package org.prgrms.kdtspringdemo.customer;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public class BlackCustomer extends Customer {

    public BlackCustomer(UUID customerId, String name, String email, String type, LocalDateTime createdAt) {
        super(customerId, name, email, type, createdAt);
        this.type = "Black";
    }

    public BlackCustomer(UUID customerId, String name, String email, String type, LocalDateTime last_loginAt, LocalDateTime createdAt) {
        super(customerId, name, email, type, last_loginAt, createdAt);
        this.type = "Black";
    }
}
