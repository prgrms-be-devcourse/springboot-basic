package org.voucherProject.voucherProject.voucher.entity;

import lombok.Getter;
import org.springframework.lang.Nullable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public abstract class Voucher implements Discountable {

    private final UUID voucherId;
    @Nullable
    private VoucherStatus voucherStatus;
    private final LocalDateTime createdAt;
    private final UUID customerId;

    protected Voucher(UUID voucherId, UUID customerId) {
        this.voucherId = voucherId;
        this.voucherStatus = VoucherStatus.VALID;
        this.createdAt = LocalDateTime.now();
        this.customerId = customerId;
    }

    protected Voucher(UUID voucherId, @Nullable VoucherStatus voucherStatus, LocalDateTime createdAt, UUID customerId) {
        this.voucherId = voucherId;
        this.voucherStatus = voucherStatus;
        this.createdAt = createdAt;
        this.customerId = customerId;
    }

    @Override
    public abstract long discount(long beforeDiscount);

    @Override
    public abstract VoucherType getVoucherType();

    @Override
    public VoucherStatus getVoucherStatus() {
        return this.voucherStatus;
    }

    @Override
    public abstract long getHowMuch();

    @Override
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public UUID getCustomerId() {
        return this.customerId;
    }

    @Override
    public void useVoucher() {
        this.voucherStatus = VoucherStatus.EXPIRED;
    }

    @Override
    public void cancelVoucher() {
        this.voucherStatus = VoucherStatus.VALID;
    }
}
