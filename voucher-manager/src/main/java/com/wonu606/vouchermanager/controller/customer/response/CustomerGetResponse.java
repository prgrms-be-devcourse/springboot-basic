package com.wonu606.vouchermanager.controller.customer.response;

import com.wonu606.vouchermanager.domain.customer.email.Email;

public class CustomerGetResponse {
    private final Email email;
    private final String nickname;

    public CustomerGetResponse(Email email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    public String getEmailAddress() {
        return email.getAddress();
    }

    public String getNickname() {
        return nickname;
    }
}
