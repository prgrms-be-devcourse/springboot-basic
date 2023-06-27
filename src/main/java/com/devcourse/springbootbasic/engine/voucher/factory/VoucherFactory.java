package com.devcourse.springbootbasic.engine.voucher.factory;

import com.devcourse.springbootbasic.engine.voucher.domain.Voucher;

public interface VoucherFactory {
    Voucher create(double voucherDiscount);
}
