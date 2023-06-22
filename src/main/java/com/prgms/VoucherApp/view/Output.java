package com.prgms.VoucherApp.view;

import com.prgms.VoucherApp.domain.Voucher;

import java.util.List;

public interface Output {
    void outputDisplayMenu();

    void outputDisplayVoucherPolicy();

    void outputVoucherHistory(List<Voucher> voucher);
}
