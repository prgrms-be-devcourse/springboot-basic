package org.devcourse.voucher.domain.voucher;

public abstract class DiscountVoucher implements Voucher{

    private final long id;
    private final VoucherType type;

    public DiscountVoucher(long id, VoucherType type) {
        this.id = id;
        this.type = setType(type);
    }

    @Override
    public Money retrievePostBalance(Money targetMoney) {
        int remainMoneyAmount = discount(targetMoney);

        return postProcessMoney(remainMoneyAmount);
    }

    protected abstract int discount(Money originPrice);

    private VoucherType setType(VoucherType type) {
        if (validateType(type)) {
            return type;
        }
        throw new RuntimeException("일치하지 않은 바우처 타입입니다");
    }

    private Money postProcessMoney(int amount) {
        if (amount < 0) {
            return Money.ZERO_AMOUNT;
        }
        return Money.of(amount);
    }

    protected abstract boolean validateType(VoucherType type);
}
