package com.prgrms.vouchermanagement.customer;

public class Customer {

    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "- name = " + name;
    }
}
