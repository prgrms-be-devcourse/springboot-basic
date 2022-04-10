package org.prgms.springbootbasic.voucher.vo;

import static com.google.common.base.Preconditions.*;

import java.util.UUID;

import com.google.common.base.Preconditions;

public class PercentDiscountVoucher implements Voucher {
	private final UUID voucherId = UUID.randomUUID();
	private final int discountPercent;

	public PercentDiscountVoucher(int discountPercent) {
		checkArgument(discountPercent >= 0 && discountPercent <= 100
			, "discountPercent(할인율)은 0 ~ 100 사이의 정수여야 합니다. discountPercent = {0}", discountPercent);

		this.discountPercent = discountPercent;
	}

	@Override
	public UUID getVoucherId() {
		return this.voucherId;
	}

	@Override
	public long discount(long beforeDiscount) {
		checkArgument(beforeDiscount >= 0, "beforeDiscount는 음수이면 안됩니다. beforeDiscount = {0}", beforeDiscount);

		return beforeDiscount - beforeDiscount * discountPercent / 100;
	}

}
