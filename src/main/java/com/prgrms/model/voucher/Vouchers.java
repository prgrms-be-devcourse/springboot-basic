package com.prgrms.model.voucher;

import java.util.List;

public class Vouchers{

    private final List<Voucher> vouchers;

    public Vouchers(List<Voucher> voucherRegistry) {
        this.vouchers = voucherRegistry;
    }

    public boolean isEmpty(List<Voucher> voucherRegistry) {
        return voucherRegistry.isEmpty();
    }

    public List<Voucher> getVouchers() {
        return vouchers.stream().toList();
    }
}
