package org.programmers.voucher.io;

import org.programmers.voucher.domain.Voucher;

import java.util.List;

public interface Output {
    void startProgram();
    void listCommand();
    void listVoucher(List<Voucher> vouchers);
    void listVoucherType();
}
