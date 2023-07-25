package com.wonu606.vouchermanager.repository.customer.resultset;

public class CustomerResultSet {

    private final String email;
    private final String nickname;

    public CustomerResultSet(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }
}
