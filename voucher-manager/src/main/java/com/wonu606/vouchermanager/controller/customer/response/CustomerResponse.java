package com.wonu606.vouchermanager.controller.customer.response;

import com.wonu606.vouchermanager.domain.customer.email.Email;

public class CustomerResponse {

    private final Email email;
    private final String nickname;

    public CustomerResponse(Email email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    public Email getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }
}
