package com.example.voucherproject.common.io.console;

import com.example.voucherproject.user.domain.User;
import com.example.voucherproject.voucher.domain.Voucher;

import java.util.List;

public interface Output {
    void exit();
    void home();
    void error();
    void createVoucher(Voucher voucher);
    void printVouchers(List<Voucher> vouchers);
    void users(List<User> list);
    void createUser(User save);
}
