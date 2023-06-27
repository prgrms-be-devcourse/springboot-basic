package com.example.voucher;

import com.example.voucher.domain.Voucher;
import java.util.ArrayList;
import java.util.List;

public class VoucherManager {
    private List<Voucher> vouchers;

    public VoucherManager() {
        this.vouchers = new ArrayList<>();
    }

    public List<Voucher> getVouchers() {
        return vouchers;
    }

    public void saveVoucher(Voucher voucher) {
        vouchers.add(voucher);
    }

}

