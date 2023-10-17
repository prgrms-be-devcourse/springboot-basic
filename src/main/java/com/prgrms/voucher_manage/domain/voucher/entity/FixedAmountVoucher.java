package com.prgrms.voucher_manage.domain.voucher.entity;

import com.prgrms.voucher_manage.console.VoucherType;

import java.util.UUID;

import static com.prgrms.voucher_manage.console.VoucherType.FIXED;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final Long discountPrice;
    private final VoucherType voucherType = FIXED;

    public FixedAmountVoucher(Long discountPrice) {
        this.voucherId = UUID.randomUUID();
        this.discountPrice = discountPrice;
    }


    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public Long getDiscountAmount() {
        return discountPrice;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }
}
