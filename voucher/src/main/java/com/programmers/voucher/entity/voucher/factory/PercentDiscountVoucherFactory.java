package com.programmers.voucher.entity.voucher.factory;

import com.programmers.voucher.entity.voucher.PercentDiscountVoucher;
import com.programmers.voucher.entity.voucher.Voucher;
import org.springframework.stereotype.Component;

@Component
public class PercentDiscountVoucherFactory implements VoucherFactory {
    @Override
    public Voucher create(String name, double value) {
        return new PercentDiscountVoucher(name, value);
    }
}
