package org.prgrms.kdt.BlackCustomer;

import lombok.Builder;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
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
