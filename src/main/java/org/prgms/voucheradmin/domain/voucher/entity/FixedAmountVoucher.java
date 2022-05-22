package org.prgms.voucheradmin.domain.voucher.entity;

import static org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType.FIXED_AMOUNT;

import java.time.LocalDateTime;
import java.util.UUID;

import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType;

/**
 * FixedAmountVoucher entity 클래스입니다. 바우처 id와, 바우처 종류, 할인 amount를 필드로 가집니다.
 **/
public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final VoucherType voucherType;
    private final long amount;
    private final LocalDateTime createdAt;

    public FixedAmountVoucher(UUID voucherId, long amount, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.voucherType = FIXED_AMOUNT;
        this.amount = amount;
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
        return this.amount;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return String.format("%s\t%s\t%d\t%s", voucherId.toString(), voucherType.getTypeName(), amount, createdAt.toString());
    }
}
