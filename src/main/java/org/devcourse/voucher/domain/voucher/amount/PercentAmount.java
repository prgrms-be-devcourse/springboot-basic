package org.devcourse.voucher.domain.voucher.amount;

public class PercentAmount extends VoucherAmount{

    public PercentAmount(int amount) {
        super(amount);
    }

    @Override
    public boolean validate(int amount) {
        return 0 <= amount && amount <= 100;
    }
}
