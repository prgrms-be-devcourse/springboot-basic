package com.devcourse.springbootbasic.application.factory;

import com.devcourse.springbootbasic.application.domain.Voucher;

public interface VoucherFactory {
    Voucher create(double voucherDiscount);
}
