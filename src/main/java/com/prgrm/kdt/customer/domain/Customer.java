package com.prgrm.kdt.customer.domain;

import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String customerName;
    private final String phoneNumber;
    private boolean isBlackList;

    public Customer(UUID customerId, String customerName, String phoneNumber) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.isBlackList = false;
    }

    public Customer(UUID customerId, String customerName, String phoneNumber, boolean isBlackList) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.isBlackList = isBlackList;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isBlackList() {
        return isBlackList;
    }

    public void setBlackList(boolean blackList) {
        isBlackList = blackList;
    }

    @Override
    public String toString() {
        return customerId + " " + customerName + " " + phoneNumber;
    }
}
