package org.programmers.VoucherManagement.io;

import org.programmers.VoucherManagement.voucher.domain.Voucher;

import java.util.List;

public interface Output {
    void printType();
    void printDiscountType();
    void printDiscountValue();
    void printExitMessage();
    void printVoucherList(List<Voucher> voucherList);
}
