package com.wonu606.vouchermanager.controller.customer.converter;

import java.util.UUID;

public class WalletDeleteParamConverter {
    private final UUID voucherUuid;
    private final String customerEmail;

    public WalletDeleteParamConverter(UUID voucherUuid, String customerEmail) {
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
