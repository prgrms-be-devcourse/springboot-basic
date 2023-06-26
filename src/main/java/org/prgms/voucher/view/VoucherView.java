package org.prgms.voucher.view;

import org.prgms.voucher.voucher.Voucher;

import java.util.List;

public interface VoucherView {

    void printOptions();

    void printVouchers(List<Voucher> vouchers);

    String readChoice();

    String readVoucherType();

    long readAmount();

    void printError(String message);
}
