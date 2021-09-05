package org.prgms.w3d1.model.wallet;

import org.prgms.w3d1.model.voucher.Voucher;

import java.util.List;

public class VoucherWallet implements Wallet{

    private final List<Voucher> voucherWallet;

    public VoucherWallet(List<Voucher> voucherWallet) {
        this.voucherWallet = voucherWallet;
    }

    public List<Voucher> getVoucherWallet() {
        return voucherWallet;
    }
}
