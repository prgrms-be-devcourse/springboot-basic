package prgms.spring_week1.domain.voucher.model.impl;

import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.domain.voucher.model.type.VoucherType;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {
    public PercentDiscountVoucher(long discount) {
        super(UUID.randomUUID(), VoucherType.PERCENT, discount);
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }
}
