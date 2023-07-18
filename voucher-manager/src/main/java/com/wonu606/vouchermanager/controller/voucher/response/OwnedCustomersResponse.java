package com.wonu606.vouchermanager.controller.voucher.response;

import java.util.List;

public class OwnedCustomersResponse {

    private final List<String> emails;

    public OwnedCustomersResponse(List<String> emails) {
        this.emails = emails;
    }

    public List<String> getEmails() {
        return emails;
    }
}
