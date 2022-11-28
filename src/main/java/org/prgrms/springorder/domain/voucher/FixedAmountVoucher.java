package org.prgrms.springorder.domain.voucher;

import static org.prgrms.springorder.domain.ErrorMessage.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

	public FixedAmountVoucher(UUID voucherId, double value, LocalDateTime createdAt) {
		super(voucherId, value, createdAt, VoucherType.FIXED_AMOUNT);
	}

	@Override
	public int discount(double beforeDiscount) {
		return (int)(beforeDiscount - getValue());
	}

	@Override
	public void validateValue(double value) {
		if (value < 0) {
			throw new IllegalArgumentException(WRONG_AMOUNT_MESSAGE.toString());
		}
	}

}
