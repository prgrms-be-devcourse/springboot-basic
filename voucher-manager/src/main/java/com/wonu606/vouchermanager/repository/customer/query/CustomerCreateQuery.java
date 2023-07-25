package com.wonu606.vouchermanager.repository.customer.query;

public class CustomerCreateQuery {

    private final String email;
    private final String nickname;

    public CustomerCreateQuery(String email, String nickname) {
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
