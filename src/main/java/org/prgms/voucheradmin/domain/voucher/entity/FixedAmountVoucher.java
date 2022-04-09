package org.prgms.voucheradmin.domain.voucher.entity;

import static org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherTypes.FIXED_AMOUNT;

import java.util.UUID;

import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherTypes;

/**
 * FixedAmountVoucher entity 클래스입니다. 바우처 id와, 바우처 종류, 할인 amount를 필드로 가집니다.
 **/
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
