package com.prgrms.model.voucher;

import java.util.List;

public class Vouchers {

    private final List<Voucher> vouchers;

    public Vouchers(List<Voucher> vouchers) {
        this.vouchers = vouchers;
    }

    public boolean isEmpty(List<Voucher> vouchers) {
        return vouchers.isEmpty();
    }

    public List<Voucher> getVouchers() {
        return vouchers.stream().toList();
    }
}
