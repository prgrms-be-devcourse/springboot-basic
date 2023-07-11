package com.prgms.VoucherApp.domain.customer.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum CustomerStatus {
    NORMAL("normal"), BLACKLIST("blacklist");

    private final String statusName;
    private static final Map<String, CustomerStatus> CUSTOMER_STATUS_MAP =
        Collections.unmodifiableMap(Arrays.stream(values())
            .collect(Collectors.toMap(CustomerStatus::getStatusName, Function.identity())));

    CustomerStatus(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }

    public static CustomerStatus findByStatus(String status) {
        return CUSTOMER_STATUS_MAP.get(status);
    }

    public static boolean containsCustomerStatus(String customerStatus) {
        return CUSTOMER_STATUS_MAP.containsKey(customerStatus);
    }

    public boolean isBlackList() {
        return this == BLACKLIST;
    }
}
