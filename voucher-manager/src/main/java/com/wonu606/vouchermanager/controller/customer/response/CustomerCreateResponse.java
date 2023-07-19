package com.wonu606.vouchermanager.controller.customer.response;

public class CustomerCreateResponse {

    private final Boolean success;

    public CustomerCreateResponse(Boolean success) {
        this.success = success;
    }

    public Boolean getSuccess() {
        return success;
    }
}
