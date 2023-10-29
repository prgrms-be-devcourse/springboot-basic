package com.prgrms.voucher_manage.domain.customer.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class Customer {
    private final UUID id;
    private final String name;
    private final CustomerType type;

    public Customer(String name, CustomerType type) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.type = type;
    }
}
