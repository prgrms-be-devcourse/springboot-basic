package org.prgms.voucherProgram.view;

import java.util.List;

import org.prgms.voucherProgram.entity.voucher.Voucher;

public interface OutputView {

    void printVoucher(Voucher voucher);

    void printAllVoucher(List<Voucher> vouchers);

    void printError(String message);
}
