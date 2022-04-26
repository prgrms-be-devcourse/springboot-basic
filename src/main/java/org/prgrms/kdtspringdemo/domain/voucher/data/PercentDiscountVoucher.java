package org.prgrms.kdtspringdemo.domain.voucher.data;

import lombok.Getter;
import org.prgrms.kdtspringdemo.domain.voucher.type.VoucherType;

import java.util.UUID;

@Getter
public class PercentDiscountVoucher extends Voucher{
    private final VoucherType type = VoucherType.PERCENT;
    private static final int MIN_DISCOUNT_AMOUNT = 0;
    private static final int MAX_DISCOUNT_AMOUNT = 100;

    public PercentDiscountVoucher(UUID voucherId, long amount) {
        super(voucherId, amount);
        validateDiscountAmount(amount);
    }

    public PercentDiscountVoucher(UUID voucherId, long amount, UUID customerId) {
        super(voucherId, amount, customerId);
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (super.getAmount() / 100);
    }

    @Override
    public VoucherType getType() {
        return this.type;
    }

    @Override
    public Voucher changeTypeAndAmount(VoucherType voucherType, int amount) {
        if(voucherType==VoucherType.FIXED){
            return new FixedAmountVoucher(this.getVoucherId(), amount, this.getCustomerId());
        }
        return new PercentDiscountVoucher(this.getVoucherId(), amount,this.getCustomerId());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PercentDiscountVoucher{");
        sb.append("voucherId=").append(super.getVoucherId());
        sb.append(", amount=").append(super.getAmount());
        sb.append(", customerId=").append(super.getCustomerId());
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }

    public void validateDiscountAmount(long amount) {
        if (amount < MIN_DISCOUNT_AMOUNT || amount > MAX_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException("Percent 입력 할인 금액은 범위에 맞지 않은 값을 입력하였습니다.");
        }
    }
}
