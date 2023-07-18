package com.wonu606.vouchermanager.controller.customer.response;

import java.util.List;

public class CustomerListGetResponse {

    private final List<String> emails;

    public CustomerListGetResponse(List<String> emails) {
        this.emails = emails;
    }

    public List<String> getEmails() {
        return emails;
    }
}
