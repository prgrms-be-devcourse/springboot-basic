package com.waterfogsw.voucher;

import com.waterfogsw.voucher.voucher.Voucher;
import com.waterfogsw.voucher.voucher.VoucherType;

import java.util.List;

public interface ApplicationController {
    void createVoucher(VoucherType type, Double value);

    List<Voucher> listVoucher();

    List<String> printBlackList();
}
