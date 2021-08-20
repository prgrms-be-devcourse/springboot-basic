package com.programmers.voucher.entity.voucher.factory;

import com.programmers.voucher.entity.voucher.Voucher;

public interface VoucherFactory {
    Voucher create(String name, double value);
}
