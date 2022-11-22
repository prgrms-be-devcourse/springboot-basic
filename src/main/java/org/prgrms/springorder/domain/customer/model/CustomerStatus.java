package org.prgrms.springorder.domain.customer.model;

import java.util.Arrays;
import java.util.Objects;

public enum CustomerStatus {

    NORMAL("normal"), BLOCKED("block");

    private final String status;

    CustomerStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static CustomerStatus of(String value) {
        return Arrays.stream(values())
            .filter(customerStatus -> Objects.equals(customerStatus.status, value.toLowerCase()))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("customer status not exists. value : " + value));
    }


}
