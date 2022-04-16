package org.prgrms.kdt.domain.voucher.model;

import org.prgrms.kdt.domain.base.BaseEntity;

import java.util.UUID;

public class PercentDiscountVoucher extends BaseEntity implements Voucher{
    private final UUID voucherId;
    private final long discountRate;
    private UUID customerId;
    private static final VoucherType voucherType = VoucherType.PERCENT_DISCOUNT;

    public PercentDiscountVoucher(UUID voucherId, long discountRate) {
        validateDiscountRate(discountRate);
        this.voucherId = voucherId;
        this.discountRate = discountRate;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (1 - (discountRate / 100));
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
    public long getDiscountValue() {
        return discountRate;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    private void validateDiscountRate(long discountRate) {
        if(discountRate > 100 || discountRate <= 0){
            throw new IllegalArgumentException("할인율은 0퍼센트 이하이거나 100퍼센트를 초과할 수 없습니다.");
        }
    }
}
