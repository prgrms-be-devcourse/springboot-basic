package com.wonu606.vouchermanager.domain.customer;

public class CustomerResultSet {

    private final String emailAddress;

    public CustomerResultSet(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}
