package com.voucher.vouchermanagement.service.customer;

import java.util.UUID;

public class CustomerJoinRequest {
    private final UUID id;
    private final String name;
    private final String email;

    public CustomerJoinRequest(UUID id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
