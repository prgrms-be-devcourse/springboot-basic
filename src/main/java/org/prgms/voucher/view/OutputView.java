package org.prgms.voucher.view;

import java.util.List;

import org.prgms.voucher.entity.Voucher;

public interface OutputView {

    void printVoucher(Voucher voucher);

    void printAllVoucher(List<Voucher> vouchers);

    void printError(String message);
}
