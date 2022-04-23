package org.prgrms.springbootbasic.entity.customer;

public class Email {

    private final String email;

    public Email(String email) {
        this.email = email;
    }

    public String showEmail() {
        return email;
    }
}
