package com.wonu606.vouchermanager.service.voucherwallet.param;

public class OwnedVouchersParam {

    private final String email;

    public OwnedVouchersParam(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
