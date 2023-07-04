package com.example.demo.customer.presentation.dto;

public class BlackCustomer {
    private String email;

    public BlackCustomer(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public static BlackCustomer fromCSV(String csv) {
        return new BlackCustomer(csv.trim());
    }
}
