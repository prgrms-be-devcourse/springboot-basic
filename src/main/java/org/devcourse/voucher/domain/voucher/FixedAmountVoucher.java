package org.devcourse.voucher.domain.voucher;

public class FixedAmountVoucher extends DiscountVoucher{

    private final int amount;

    public FixedAmountVoucher(long id, VoucherType type, int amount) {
        super(id, type);
        this.amount = amount;
    }

    @Override
    protected int discount(Money originPrice) {
        return originPrice.amount() - amount;
    }
}
