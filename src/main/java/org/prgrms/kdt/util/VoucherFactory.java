package org.prgrms.kdt.util;

import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.Amount;
import org.prgrms.kdt.model.FixedAmount;
import org.prgrms.kdt.model.PercentAmount;
import org.prgrms.kdt.model.domain.Voucher;
import org.prgrms.kdt.model.dto.VoucherRequest;
import org.prgrms.kdt.model.entity.VoucherEntity;

public final class VoucherFactory {
	private static final IdGenerator idGenerator = new IdGenerator();

	public static Voucher createVoucherDomain(int amount, String voucherTypeString) {
		Long voucherId = idGenerator.getRandomId();
		VoucherType voucherType = VoucherType.value(voucherTypeString);
		Amount voucherAmount = getVoucherAmount(amount, voucherType);
		return new Voucher(voucherId, voucherAmount, voucherType);
	}

	public static VoucherEntity createVoucherEntity(VoucherRequest voucherRequest) {
		Long voucherId = idGenerator.getRandomId();
		return new VoucherEntity(voucherId, voucherRequest.getAmount(), voucherRequest.getVoucherType());
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
