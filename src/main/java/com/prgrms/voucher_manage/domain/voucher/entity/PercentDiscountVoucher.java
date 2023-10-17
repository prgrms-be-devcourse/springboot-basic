package com.prgrms.voucher_manage.domain.voucher.entity;

import com.prgrms.voucher_manage.console.VoucherType;

import java.util.UUID;

import static com.prgrms.voucher_manage.console.VoucherType.*;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final Long discountPercent;
    private final VoucherType voucherType = PERCENT;

    public PercentDiscountVoucher(Long discountPercent) {
        this.voucherId = UUID.randomUUID();
        this.discountPercent = discountPercent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public Long getDiscountAmount() {
        return discountPercent;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }


}
