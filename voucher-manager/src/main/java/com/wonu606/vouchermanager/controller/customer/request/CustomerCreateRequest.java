package com.wonu606.vouchermanager.controller.customer.request;

public class CustomerCreateRequest {

    private String email;
    private String nickname;

    public CustomerCreateRequest() {
    }

    public CustomerCreateRequest(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
