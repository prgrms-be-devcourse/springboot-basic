package org.prgrms.kdtspringdemo.domain.voucher.data;

import org.prgrms.kdtspringdemo.domain.voucher.type.VoucherType;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private long amount;
    private UUID customerId;
    private final String type = "FIXED";
    private static final int MIN_DISCOUNT_AMOUNT = 0;
    private static final int MAX_DISCOUNT_AMOUNT = 1_000_000;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        validateDiscountAmount(amount);
        this.voucherId = voucherId;
        this.amount = amount;
        this.customerId = UUID.nameUUIDFromBytes("null".getBytes());
    }

    public FixedAmountVoucher(UUID voucherId, long amount, UUID customerId) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.customerId = customerId;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public UUID getCustomerId() {
        return customerId;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public long getAmount() {
        return amount;
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
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                ", customerId=" + customerId +
                ", type='" + type + '\'' +
                '}';
    }

    public void validateDiscountAmount(long amount) {
        if (amount < MIN_DISCOUNT_AMOUNT || amount > MAX_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException("Fixed 입력 할인 금액은 범위에 맞지 않은 값을 입력하였습니다.");
        }
    }
}
