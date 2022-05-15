package com.dojinyou.devcourse.voucherapplication.voucher.entity;

import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherAmount;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherType;

import java.time.LocalDateTime;

public class PercentAmountVoucherEntity extends VoucherEntity {
    public PercentAmountVoucherEntity(Long id, VoucherType type, VoucherAmount amount,
                                      LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, type, amount, createdAt, updatedAt);
    }
}
