package org.programmers.kdt.io;

import org.programmers.kdt.voucher.Voucher;

import java.util.List;

public interface Output {
    void inputError(String message);
    void printSuccessAddVoucher(Voucher message);
    void sayGoodBye();
    void printAllVouchersInfo(List<Voucher> voucherList);
}
