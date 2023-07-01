package com.prgrms.model.voucher;

import java.util.List;

public class VoucherRegistry {

    private final List<Voucher> voucherRegistry;

    public VoucherRegistry(List<Voucher> voucherRegistry) {
        this.voucherRegistry = voucherRegistry;
    }

    public boolean isEmpty(List<Voucher> voucherRegistry ){
        return voucherRegistry.isEmpty();
    }

    public List<Voucher> getVoucherRegistry () {
        return voucherRegistry.stream().toList();
    }
}
