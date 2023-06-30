package com.programmers.springweekly.domain.customer;

public enum CustomerType {
    NORMAL,
    BLACKLIST;

    public static CustomerType findCustomerType(String type) {
        try {
            return valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Input: " + type + ", The type you are looking for is not found.");
        }
    }

    public static boolean isBlacklistedCustomer(CustomerType customerType) {
        return customerType == CustomerType.BLACKLIST;
    }
}
