package com.wonu606.vouchermanager.service.customer.param;

public class CustomerCreateParam {

    private final String email;
    private final String nickname;

    public CustomerCreateParam(String email, String nickname) {
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
