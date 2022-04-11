package com.example.voucherproject.common.io;

import com.example.voucherproject.common.enums.VoucherType;
import com.example.voucherproject.user.domain.User;
import com.example.voucherproject.voucher.domain.Voucher;

import java.util.List;

public interface Output {
    void exit();
    void error();
    void createVoucher(Voucher voucher);
    void vouchers(List<Voucher> v);
    void users(List<User> list);
    void createUser(User save);
}
