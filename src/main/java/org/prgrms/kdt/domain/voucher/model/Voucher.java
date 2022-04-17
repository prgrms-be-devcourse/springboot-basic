package org.prgrms.kdt.domain.voucher.model;

import org.prgrms.kdt.domain.base.BaseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Voucher extends BaseEntity{
    private final UUID voucherId;
    private VoucherType voucherType;
    private Long discountValue;
    private UUID customerId;

    public Voucher(UUID voucherId, VoucherType voucherType, Long discountValue, UUID customerId, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        super(createdDate, modifiedDate);
        validateDiscountValue(discountValue, voucherType);
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discountValue = discountValue;
        this.customerId = customerId;
    }

    public Voucher(UUID voucherId, VoucherType voucherType, Long discountValue, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        super(createdDate, modifiedDate);
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discountValue = discountValue;
    }

    private void validateDiscountValue(Long discountValue, VoucherType voucherType) {
        switch (voucherType) {
            case FIXED_AMOUNT:
                if (discountValue <= 0) {
                    throw new IllegalArgumentException("할인금액은 0원 이하가 될 수 없습니다.");
                }
                break;
            case PERCENT_DISCOUNT:
                if (discountValue > 100 || discountValue <= 0) {
                    throw new IllegalArgumentException("할인율은 0퍼센트 이하이거나 100퍼센트를 초과할 수 없습니다.");
                }
                break;
        }
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public Long getDiscountValue() {
        return discountValue;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public long discount(long beforeDiscount) {
        return switch (voucherType) {
            case FIXED_AMOUNT -> beforeDiscount - discountValue;
            case PERCENT_DISCOUNT -> beforeDiscount * (1 - (discountValue / 100));
        };
    }

    @Override
    public String toString() {
        return "Voucher{" +
                "voucherId=" + voucherId +
                ", voucherType=" + voucherType +
                ", discountValue=" + discountValue +
                ", customerId=" + customerId +
                '}';
    }
}
