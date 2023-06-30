package com.devcourse.springbootbasic.application.util.factory;

import com.devcourse.springbootbasic.application.domain.Voucher;

public interface VoucherFactory {
    Voucher create(double voucherDiscount);
}
