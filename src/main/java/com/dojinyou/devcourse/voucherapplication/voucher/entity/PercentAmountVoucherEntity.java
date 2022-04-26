package com.dojinyou.devcourse.voucherapplication.voucher.entity;

import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherAmount;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherType;

public class PercentAmountVoucherEntity extends VoucherEntity {
    public PercentAmountVoucherEntity(Long id, VoucherType type, VoucherAmount amount) {
        super(id, type, amount);
    }
}
