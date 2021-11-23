package org.prgrms.kdt.service.dto;

import java.util.UUID;

public class RequestUpdateCustomerDto {

    private final UUID customerId;
    private final String name;

    public RequestUpdateCustomerDto(UUID customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}
