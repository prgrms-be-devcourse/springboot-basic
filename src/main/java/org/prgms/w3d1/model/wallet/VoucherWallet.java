package org.prgms.w3d1.model.wallet;

import org.prgms.w3d1.model.voucher.Voucher;

import java.util.List;
import java.util.UUID;

public class VoucherWallet implements Wallet{

    private final UUID VoucherWalletId;
    private final List<Voucher> voucherWallet;
    private final UUID customerId;

    public VoucherWallet(UUID voucherWalletId, List<Voucher> voucherWallet, UUID customerId) {
        VoucherWalletId = voucherWalletId;
        this.voucherWallet = voucherWallet;
        this.customerId = customerId;
    }

    public UUID getVoucherWalletId() {
        return VoucherWalletId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public List<Voucher> getVoucherWallet() {
        return voucherWallet;
    }
}
