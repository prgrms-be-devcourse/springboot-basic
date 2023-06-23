package org.devcourse.voucher.domain.voucher.amount;

public class FixedAmount extends VoucherAmount{

    public FixedAmount(int amount) {
        super(amount);
    }

    @Override
    public boolean validate(int amount) {
        return 0 <= amount;
    }
}
