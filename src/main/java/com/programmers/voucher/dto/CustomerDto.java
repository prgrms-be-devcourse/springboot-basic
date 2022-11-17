package com.programmers.voucher.dto;

public class CustomerDto {
    private final String customerName;
    private final String email;

    public CustomerDto(String customerName, String email) {
        this.customerName = customerName;
        this.email = email;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getEmail() {
        return email;
    }
}
