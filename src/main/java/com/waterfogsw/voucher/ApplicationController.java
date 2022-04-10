package com.waterfogsw.voucher;

import com.waterfogsw.voucher.voucher.Voucher;

import java.util.List;

public interface ApplicationController {
    void createVoucher();

    List<Voucher> listVoucher();

    List<String> printBlackList();
}
