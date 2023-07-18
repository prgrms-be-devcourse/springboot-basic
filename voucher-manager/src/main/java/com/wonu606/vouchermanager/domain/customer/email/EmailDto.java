package com.wonu606.vouchermanager.domain.customer.email;

public class EmailDto {
    String emailAddress;

    public EmailDto(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}
