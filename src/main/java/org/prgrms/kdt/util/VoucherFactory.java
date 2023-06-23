package org.prgrms.kdt.util;

import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.Amount;
import org.prgrms.kdt.model.FixedAmount;
import org.prgrms.kdt.model.PercentAmount;
import org.prgrms.kdt.model.dto.VoucherDTO;

public class VoucherFactory {

	public static VoucherDTO getVoucherDTO(int amount, VoucherType voucherType) {
		Amount voucherAmount = getVoucherAmount(amount, voucherType);
		return new VoucherDTO(voucherAmount, voucherType);
	}

	public static Amount getVoucherAmount(int amount, VoucherType voucherType) {
		switch (voucherType) {
			case FixedAmountVoucher -> {
				return new FixedAmount(amount);
			}

			case PercentDiscountVoucher -> {
				return new PercentAmount(amount);
			}

			default -> {
				throw new IllegalArgumentException("존재 하지 않는 voucher type 입니다.");
			}
		}
	}
}
