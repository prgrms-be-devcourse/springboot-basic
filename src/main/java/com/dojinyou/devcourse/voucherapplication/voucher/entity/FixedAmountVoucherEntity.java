package com.dojinyou.devcourse.voucherapplication.voucher.entity;

import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherAmount;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherType;

public class FixedAmountVoucherEntity extends VoucherEntity {
    public FixedAmountVoucherEntity(Long id, VoucherType type, VoucherAmount amount) {
        super(id, type, amount);
    }
}
