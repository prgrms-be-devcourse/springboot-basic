package com.prgrms.voucher_manage.domain.voucher.entity;

import com.prgrms.voucher_manage.console.VoucherType;

import java.util.UUID;

import static com.prgrms.voucher_manage.console.VoucherType.*;

public class PercentAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final Long discountPercent;
    private final VoucherType voucherType = PERCENT;

    public PercentAmountVoucher(Long discountPercent) {
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
