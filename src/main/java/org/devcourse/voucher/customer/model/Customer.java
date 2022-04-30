package org.devcourse.voucher.customer.model;

import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private String name;
    private Email email;
    private boolean ban;

    public Customer(UUID customerId, String name) {
        this.customerId = customerId;
        this.name = name;
        this.ban = false;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBan() {
        return ban;
    }

    public void setBan(boolean ban) {
        this.ban = ban;
    }

    @Override
    public String toString() {
        return customerId + "\t" + name;
    }

    public UUID getCustomerId() {
        return customerId;
    }
}
