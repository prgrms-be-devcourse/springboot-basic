package org.prgrms.kdt.domain.voucher.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher{
    private final UUID voucherId;
    private final long discountPrice;
    private UUID customerId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private static final VoucherType voucherType = VoucherType.FIXED_AMOUNT;

    public FixedAmountVoucher(UUID voucherId, long discountPrice) {
        validateDiscountPrice(discountPrice);
        this.voucherId = voucherId;
        this.discountPrice = discountPrice;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - discountPrice;
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
        return discountPrice;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    private void validateDiscountPrice(long discountPrice) {
        if(discountPrice <= 0) {
            throw new IllegalArgumentException("할인금액은 0원 이하가 될 수 없습니다.");
        }
    }
}
