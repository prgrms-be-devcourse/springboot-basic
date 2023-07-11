package com.wonu606.vouchermanager.domain.customer;

public class CustomerDto {

    private final String emailAddress;
    private final String nickname;

    public CustomerDto(String emailAddress, String nickname) {
        this.emailAddress = emailAddress;
        this.nickname = nickname;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getNickname() {
        return nickname;
    }
}
