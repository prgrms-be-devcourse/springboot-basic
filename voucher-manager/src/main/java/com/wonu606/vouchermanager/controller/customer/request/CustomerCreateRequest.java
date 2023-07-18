package com.wonu606.vouchermanager.controller.customer.request;

public class CustomerCreateRequest {

    private final String email;
    private final String nickname;

    public CustomerCreateRequest(String email, String nickname) {
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
