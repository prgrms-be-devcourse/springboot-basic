package org.prgms.voucheradmin.domain.voucher.entity;

import static org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType.PERCENTAGE_DISCOUNT;

import java.time.LocalDateTime;
import java.util.UUID;

import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType;

/**
 * PercentageDiscountVoucher entity 클래스입니다. 바우처 id와, 바우처 종류, 할인 percent를 필드로 가집니다.
 **/
public class PercentageDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final VoucherType voucherType;
    private final int percent;
    private final LocalDateTime createdAt;

    public PercentageDiscountVoucher(UUID voucherId, int percent, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.voucherType = PERCENTAGE_DISCOUNT;
        this.percent = percent;
        this.createdAt = createdAt;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public long getAmount() {
        return this.percent;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public long discount(long beforeDiscount) {
        return (long)(beforeDiscount - (beforeDiscount / 100.0 * percent));
    }

    @Override
    public String toString() {
        return String.format("%s\t%s\t%d", voucherId, voucherType.getTypeName(), percent);
    }
}
