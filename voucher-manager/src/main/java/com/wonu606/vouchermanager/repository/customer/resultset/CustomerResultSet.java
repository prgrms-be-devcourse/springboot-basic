package com.wonu606.vouchermanager.repository.customer.resultset;

public class CustomerResultSet {

    private final String emailAddress;
    private final String nickname;

    public CustomerResultSet(String emailAddress, String nickname) {
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
