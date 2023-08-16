package org.prgrms.kdt.model.domain;

import org.prgrms.kdt.common.codes.ErrorCode;
import org.prgrms.kdt.common.exception.VoucherRuntimeException;
import org.prgrms.kdt.enums.VoucherType;

public class PercentVoucher extends Voucher {

	public PercentVoucher(Long voucherId, int amount, VoucherType voucherType) {
		super(voucherId, amount, voucherType);
		if (!validate(amount)) {
			throw new VoucherRuntimeException(ErrorCode.VOUCHER_PERCENT_AMOUNT_ERROR);
		}
	}

	@Override
	public int totalAmount(int beforeAmount) {
		return beforeAmount - beforeAmount * amount / 100;

	}

	public boolean validate(int amount) {
		return amount > 0 && amount <= 100;
	}
}
