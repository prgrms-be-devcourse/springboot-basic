package com.programmers.springweekly.domain.customer;

public enum CustomerType {
    NORMAL,
    BLACKLIST;

    public static CustomerType findCustomerType(String type) {
        try {
            return valueOf(type.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Input: " + type + ", The type you are looking for is not found.");
        }
    }
    
    public static boolean isBlacklistedCustomer(CustomerType customerType) {
        if (customerType == CustomerType.BLACKLIST) {
            return true;
        }

        return false;
    }
}
