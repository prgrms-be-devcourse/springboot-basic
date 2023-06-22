package org.devcourse.voucher.domain.voucher;

public abstract class DiscountVoucher implements Voucher{

    private final long id;
    private final VoucherType type;

    public DiscountVoucher(long id, VoucherType type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public Money checkBalance(Money targetMoney) {
        int remainMoneyAmount = discount(targetMoney);

        return Money.of(remainMoneyAmount);
    }

    @Override
    public long getId() {
        return this.id;
    }

    protected abstract int discount(Money originPrice);
}
