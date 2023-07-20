package com.wonu606.vouchermanager.controller.voucherwallet.response;

public class VoucherCreateResponse {

    private final boolean success;

    public VoucherCreateResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
