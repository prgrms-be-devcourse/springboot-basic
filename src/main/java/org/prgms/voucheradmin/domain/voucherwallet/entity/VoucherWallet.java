package org.prgms.voucheradmin.domain.voucherwallet.entity;

import java.util.UUID;

public class VoucherWallet {
    private UUID voucherWalletId;
    private UUID customerId;
    private UUID voucherId;

    public VoucherWallet(UUID voucherWalletId, UUID customerId, UUID voucherId) {
        this.voucherWalletId = voucherWalletId;
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

    public UUID getVoucherWalletId() {
        return voucherWalletId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }
}
