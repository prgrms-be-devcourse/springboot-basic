package org.prgms.kdtspringvoucher.customer.domain;

public enum CustomerType {
    BASIC("basic"),
    BLACKLIST("blacklist");

    private String customerType;

    CustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerType() {
        return customerType;
    }
}
