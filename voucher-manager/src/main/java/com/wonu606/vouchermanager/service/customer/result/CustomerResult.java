package com.wonu606.vouchermanager.service.customer.result;

public class CustomerResult {

    private final String email;
    private final String nickname;

    public CustomerResult(String email, String nickname) {
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
