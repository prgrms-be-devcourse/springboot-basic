package org.prgms.springbootbasic.voucher.vo;

import static com.google.common.base.Preconditions.*;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

	public PercentDiscountVoucher(int value) {
		super(UUID.randomUUID(), VoucherType.PERCENTDISCOUNTVOUCHER, value);

		checkArgument(value >= 0 && value <= 100
			, "discountPercent(할인율)은 0 ~ 100 사이의 정수여야 합니다. discountPercent = {0}", value);

	}

	public PercentDiscountVoucher(UUID voucherId, VoucherType type, int value) {
		super(voucherId, type, value);
	}


	public long discount(long beforeDiscount) {
		checkArgument(beforeDiscount >= 0, "beforeDiscount는 음수이면 안됩니다. beforeDiscount = {0}", beforeDiscount);

		return beforeDiscount - beforeDiscount * getValue() / 100;
	}

}
