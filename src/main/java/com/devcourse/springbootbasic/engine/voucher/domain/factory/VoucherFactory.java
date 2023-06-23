package com.devcourse.springbootbasic.engine.voucher.domain.factory;

import com.devcourse.springbootbasic.engine.voucher.domain.Voucher;

public interface VoucherFactory {
    Voucher create(double voucherDiscount);
}
