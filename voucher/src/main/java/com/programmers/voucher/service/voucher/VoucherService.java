package com.programmers.voucher.service.voucher;

import com.programmers.voucher.entity.voucher.DiscountPolicy;
import com.programmers.voucher.entity.voucher.Voucher;

import java.util.List;

public interface VoucherService {
    void openStorage();

    void closeStorage();

    Voucher create(String name, DiscountPolicy.Type type, int value);

    List<Voucher> listAll();
}
