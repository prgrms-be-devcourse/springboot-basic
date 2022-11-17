package org.prgrms.springorder.domain.voucher;

import static org.prgrms.springorder.domain.ErrorMessage.*;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

	public PercentDiscountVoucher(UUID voucherId, double value) {
		super(voucherId, value);
	}

	@Override
	public int discount(double beforeDiscount) {
		return (int)(beforeDiscount - (beforeDiscount * (getValue() / 100)));
	}

	@Override
	public void validateValue(double value) {
		if (value < 0 || value > 100) {
			throw new IllegalArgumentException(WRONG_PERCENT_MESSAGE.toString());
		}
	}

	@Override
	public String toString() {
		return String.format("%s,fixedAmount,%d", getVoucherId(), (int)getValue());
	}
}
