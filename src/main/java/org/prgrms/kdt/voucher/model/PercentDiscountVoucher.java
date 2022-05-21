package org.prgrms.kdt.voucher.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private static final int MIN_PERCENT = 0;
    private static final int MAX_PERCENT = 100;

    private final UUID id;
    private long value;
    private final LocalDateTime createdAt;
    private final VoucherType type = VoucherType.PERCENT;

    public PercentDiscountVoucher(UUID id, long value, LocalDateTime createdAt) {
        if (value < MIN_PERCENT || value > MAX_PERCENT) {
            throw new IllegalArgumentException("퍼센트는 1~100 사이의 값을 입력해주세요.");
        }

        this.id = id;
        this.value = value;
        this.createdAt = createdAt;
    }

    public PercentDiscountVoucher(long value) {
        if (value < MIN_PERCENT || value > MAX_PERCENT) {
            throw new IllegalArgumentException("퍼센트는 1~100 사이의 값을 입력해주세요.");
        }

        this.id = UUID.randomUUID();
        this.value = value;
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
            "id=" + id +
            ", percent=" + value +
            '}';
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (this.value / 100);
    }

    @Override
    public VoucherType getVoucherType() {
        return this.type;
    }

    @Override
    public long getValue() {
        return this.value;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public VoucherType getType() {
        return type;
    }

}
