package org.devcourse.voucher.domain.voucher.amount;

public class FixedAmount extends VoucherAmount{

    public FixedAmount(int amount) {
        super(amount);
    }

    @Override
    public boolean inValid(int amount) {
        return 0 > amount || amount > 100_000;
    }
}
