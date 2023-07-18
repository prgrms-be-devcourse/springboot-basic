package com.wonu606.vouchermanager.controller.voucher.response;

import java.util.List;

public class VoucherGetOwnedCustomersResponse {

    private final List<String> emails;

    public VoucherGetOwnedCustomersResponse(List<String> emails) {
        this.emails = emails;
    }

    public List<String> getEmails() {
        return emails;
    }
}
