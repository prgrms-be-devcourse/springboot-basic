package org.prgms.voucheradmin.domain.voucher.entity;

import static org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherTypes.FIXED_AMOUNT;

import java.util.UUID;

import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherTypes;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final VoucherTypes voucherTypes;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.voucherTypes = FIXED_AMOUNT;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public VoucherTypes getVoucherType() {
        return voucherTypes;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public String toString() {
        StringBuilder voucherInfoBuilder = new StringBuilder();
        voucherInfoBuilder.append(voucherId).append("\t").append(voucherTypes.getTypeName()).append("\t").append(amount);
        return voucherInfoBuilder.toString();
    }
}
