package com.demo.voucher.io;

import com.demo.voucher.domain.Voucher;

import java.util.Map;
import java.util.UUID;

public interface Output {
    void showMenu();

    void showVoucherType();

    void inputError(String errorResponse);

    void showAllVouchers(Map<UUID, Voucher> voucherHistory);
}
