package com.dojinyou.devcourse.voucherapplication.view;

import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherAmount;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherType;

public interface InputView<T> {
    T getUserInput();

    VoucherAmount getVoucherAmount(VoucherType voucherType);

    VoucherType getVoucherType();
}
