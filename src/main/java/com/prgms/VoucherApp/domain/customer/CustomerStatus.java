package com.prgms.VoucherApp.domain.customer;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum CustomerStatus {
    NORMAL("normal"), BLACKLIST("blacklist");

    private final String status;
    private static final Map<String, CustomerStatus> CUSTOMER_STATUS_MAP =
            Collections.unmodifiableMap(Arrays.stream(values())
                    .collect(Collectors.toMap(CustomerStatus::getStatus, Function.identity())));

    CustomerStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public static CustomerStatus findByStatus(String status) {
        return CUSTOMER_STATUS_MAP.get(status);
    }

    public boolean isBlackList() {
        return this == BLACKLIST;
    }
}
