package com.wonu606.vouchermanager.service.voucherwallet.param;

import java.util.UUID;

public class WalletDeleteParam {

    private final UUID voucherUuid;
    private final String customerEmail;

    public WalletDeleteParam(UUID voucherUuid, String customerEmail) {
        this.voucherUuid = voucherUuid;
        this.customerEmail = customerEmail;
    }

    public UUID getVoucherUuid() {
        return voucherUuid;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }
}
