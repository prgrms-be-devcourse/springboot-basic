package com.wonu606.vouchermanager.domain.voucherwallet;

import java.util.Objects;
import java.util.UUID;

public class VoucherWallet {

    private final UUID voucherUuid;
    private final String email;

    public VoucherWallet(UUID voucherUuid, String email) {
        this.voucherUuid = voucherUuid;
        this.email = email;
    }

    public UUID getVoucherUuid() {
        return voucherUuid;
    }

    public String getEmailAddress() {
        return email;
    }
}
