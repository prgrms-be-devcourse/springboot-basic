package org.prgrms.kdt.blackCustomer.domain;

import lombok.Builder;

import java.util.UUID;

public class BlackCustomer {
    private UUID customerId;
    private String name;
    private String phone;

    @Builder
    public BlackCustomer(UUID customerId, String name, String phone) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getPhone() {
        return phone;
    }

}
