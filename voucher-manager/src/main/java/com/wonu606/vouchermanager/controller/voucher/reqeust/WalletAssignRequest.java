package com.wonu606.vouchermanager.controller.voucher.reqeust;

import java.util.UUID;

public class WalletAssignRequest {

    private final String voucherId;
    private final String email;

    public WalletAssignRequest(String voucherId, String email) {
        this.voucherId = voucherId;
        this.email = email;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public String getEmail() {
        return email;
    }
}
