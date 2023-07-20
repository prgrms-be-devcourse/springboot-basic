package com.wonu606.vouchermanager.controller.voucherwallet.response;

public class OwnedCustomerResponse {

    private final String email;

    public OwnedCustomerResponse(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
