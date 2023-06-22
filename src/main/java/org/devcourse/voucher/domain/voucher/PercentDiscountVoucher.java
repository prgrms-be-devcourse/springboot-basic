package org.devcourse.voucher.domain.voucher;

public class PercentDiscountVoucher extends DiscountVoucher{

    private final int percent;

    public PercentDiscountVoucher(long id, VoucherType type, int percent) {
        super(id, type);
        this.percent = percent;
    }

    @Override
    protected int discount(Money originPrice) {
        return originPrice.amount() * percent / 100;
    }

    @Override
    protected boolean validateType(VoucherType type) {
        return type.equals(VoucherType.PERCENT);
    }

}
