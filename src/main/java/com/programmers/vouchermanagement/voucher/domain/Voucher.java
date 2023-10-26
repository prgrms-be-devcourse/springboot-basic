package com.programmers.vouchermanagement.voucher.domain;

import java.util.UUID;

public class Voucher {

    private final UUID voucherId;
    private final Long discount;
    private final VoucherType voucherType;
    private final VoucherPolicy voucherPolicy;

    public Voucher(UUID voucherId, Long discount, VoucherType voucherType, VoucherPolicy voucherPolicy) {

        voucherPolicy.validateDiscount(discount);

        this.voucherId = voucherId;
        this.discount = discount;
        this.voucherType = voucherType;
        this.voucherPolicy = voucherPolicy;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public Long getDiscount() {
        return discount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }
}
