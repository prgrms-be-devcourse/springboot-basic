package com.programmers.springmission.voucher.domain;

import com.programmers.springmission.voucher.domain.enums.VoucherType;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Voucher {

    private final UUID voucherId;
    private final VoucherPolicy voucherPolicy;
    private final VoucherType voucherType;

    public Voucher(UUID voucherId, VoucherPolicy voucherPolicy, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.voucherPolicy = voucherPolicy;
        this.voucherType = voucherType;
    }

    public long discount(long beforeDiscount) {
        return voucherPolicy.discount(beforeDiscount);
    }
}

