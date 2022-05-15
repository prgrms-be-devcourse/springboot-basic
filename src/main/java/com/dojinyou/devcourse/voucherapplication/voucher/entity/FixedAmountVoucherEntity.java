package com.dojinyou.devcourse.voucherapplication.voucher.entity;

import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherAmount;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherType;

import java.time.LocalDateTime;

public class FixedAmountVoucherEntity extends VoucherEntity {
    public FixedAmountVoucherEntity(Long id, VoucherType type, VoucherAmount amount,
                                    LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, type, amount, createdAt, updatedAt);
    }
}
