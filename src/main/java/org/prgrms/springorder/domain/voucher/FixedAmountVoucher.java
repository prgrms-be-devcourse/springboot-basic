package org.prgrms.springorder.domain.voucher;

import java.util.UUID;

import org.prgrms.springorder.domain.ErrorMessage;

public class FixedAmountVoucher extends Voucher {

	public FixedAmountVoucher(UUID voucherId, double value) {
		super(voucherId, value);
	}

	@Override
	public int discount(double beforeDiscount) {
		return (int)(beforeDiscount - getValue());
	}

	@Override
	public void validateValue(double value) {
		if (value < 0) {
			throw new IllegalArgumentException(ErrorMessage.WRONG_AMOUNT_MESSAGE.toString());
		}
	}

	@Override
	public String toString() {
		return String.format("%s,fixedAmount,%d", getVoucherId(), (int)getValue());
	}
}
