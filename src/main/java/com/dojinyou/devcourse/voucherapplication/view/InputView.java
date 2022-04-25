package com.dojinyou.devcourse.voucherapplication.view;

import com.dojinyou.devcourse.voucherapplication.voucher.Entity.VoucherAmount;
import com.dojinyou.devcourse.voucherapplication.voucher.Entity.VoucherType;

public interface InputView<T> {
    T getUserInput();

    VoucherAmount getVoucherAmount(VoucherType voucherType);

    VoucherType getVoucherType();
}
