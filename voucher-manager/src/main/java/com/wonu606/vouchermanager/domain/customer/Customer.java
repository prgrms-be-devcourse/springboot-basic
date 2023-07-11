package com.wonu606.vouchermanager.domain.customer;

public class Customer {

    private final EmailAddress emailAddress;

    public Customer(EmailAddress emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress.getAddress();
    }
}
