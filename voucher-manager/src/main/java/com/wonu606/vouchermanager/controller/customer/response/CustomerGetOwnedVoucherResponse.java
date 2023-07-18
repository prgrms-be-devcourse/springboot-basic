package com.wonu606.vouchermanager.controller.customer.response;

public class CustomerGetOwnedVoucherResponse {
    private final String voucherId;

    public CustomerGetOwnedVoucherResponse(String classSimpleName, String voucherId,
            String discountValueType, String discountValue) {
        this.voucherId = voucherId;
    }

    public String getVoucherId() {
        return voucherId;
    }
}
