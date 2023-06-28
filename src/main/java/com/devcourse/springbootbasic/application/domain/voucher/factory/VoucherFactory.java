package com.devcourse.springbootbasic.application.domain.voucher.factory;

import com.devcourse.springbootbasic.application.domain.voucher.data.Voucher;

public interface VoucherFactory {
    Voucher create(double voucherDiscount);
}
