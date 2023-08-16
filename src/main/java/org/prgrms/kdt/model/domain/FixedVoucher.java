package org.prgrms.kdt.model.domain;

import org.prgrms.kdt.common.codes.ErrorCode;
import org.prgrms.kdt.common.exception.VoucherRuntimeException;
import org.prgrms.kdt.enums.VoucherType;

public class FixedVoucher extends Voucher {

	public FixedVoucher(Long voucherId, int amount, VoucherType voucherType) {
		super(voucherId, amount, voucherType);
		if (!validate(amount)) {
			throw new VoucherRuntimeException(ErrorCode.VOUCHER_FIXED_AMOUNT_ERROR);
		}
	}

	public boolean validate(int amount) {
		return amount >= 1;
	}

	@Override
	public int totalAmount(int beforeAmount) {
		int afterAmount = beforeAmount - super.amount;

		return Math.max(afterAmount, 0);
	}

}
