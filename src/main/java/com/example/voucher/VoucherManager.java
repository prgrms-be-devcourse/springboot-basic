package com.example.voucher;

import com.example.voucher.domain.Voucher;
import com.example.voucher.ui.Output;
import java.util.ArrayList;
import java.util.List;

public class VoucherManager {
    private List<Voucher> vouchers;

    public VoucherManager() {
        this.vouchers = new ArrayList<>();
    }

    public void createVoucher(Voucher voucher) {
        vouchers.add(voucher);
    }

    public void listVouchers() {
        for (Voucher voucher : vouchers) {
            Output.printVoucherInfo(voucher);
        }
    }
}

