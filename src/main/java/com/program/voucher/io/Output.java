package com.program.voucher.io;

import com.program.voucher.model.Voucher;

import java.util.List;

public interface Output {

    void menuView();

    void errorMessage(String message);

    void successMessage(String message);

    void allVoucherView(List<Voucher> vouchers);

    void customerBlackListView(List<String> blackList);
}
