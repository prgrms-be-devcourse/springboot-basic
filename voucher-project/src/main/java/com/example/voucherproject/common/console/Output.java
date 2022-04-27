package com.example.voucherproject.common.console;

import com.example.voucherproject.user.domain.User;
import com.example.voucherproject.voucher.domain.Voucher;
import com.example.voucherproject.wallet.domain.Wallet;

import java.util.List;

public interface Output {
    void exit();
    void home();
    void error();
    void createVoucher(Voucher voucher);
    void printVouchers(List<Voucher> vouchers);
    void printUsers(List<User> list);
    void printWallets(List<Wallet> all);
    void createUser(User save);

    void selectedUser(User user);
    void selectedVoucher(Voucher voucher);

    void deleteSuccess(Wallet value);

    void printWalletVouchers(List<Wallet> wallets);

    void printWalletUsers(List<Wallet> wallets);
}
