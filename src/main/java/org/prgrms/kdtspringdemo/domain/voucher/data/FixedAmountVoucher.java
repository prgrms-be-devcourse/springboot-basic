package org.prgrms.kdtspringdemo.domain.voucher.data;

import org.prgrms.kdtspringdemo.domain.voucher.type.VoucherType;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    private final VoucherType type = VoucherType.FIXED;
    private static final int MIN_DISCOUNT_AMOUNT = 0;
    private static final int MAX_DISCOUNT_AMOUNT = 1_000_000;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        super(voucherId, amount);
        validateDiscountAmount(amount);
    }

    public FixedAmountVoucher(UUID voucherId, long amount, UUID customerId) {
        super(voucherId,amount,customerId);
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - super.getAmount();
    }

    @Override
    public VoucherType getType() {
        return this.type;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FixedAmountVoucher{");
        sb.append("voucherId=").append(super.getVoucherId());
        sb.append(", amount=").append(super.getAmount());
        sb.append(", customerId=").append(super.getCustomerId());
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }

    public void validateDiscountAmount(long amount) {
        if (amount < MIN_DISCOUNT_AMOUNT || amount > MAX_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException("Fixed 입력 할인 금액은 범위에 맞지 않은 값을 입력하였습니다.");
        }
    }
}
