package org.prgms.voucherProgram.view;

import java.util.List;

import org.prgms.voucherProgram.entity.user.User;
import org.prgms.voucherProgram.entity.voucher.Voucher;

public interface OutputView {

    void printVoucher(Voucher voucher);

    void printVouchers(List<Voucher> vouchers);

    void printUsers(List<User> users);

    void printError(String message);
}
