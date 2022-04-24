package org.prgms.springbootbasic.voucher.vo;

import static com.google.common.base.Preconditions.*;

import java.text.MessageFormat;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
	public FixedAmountVoucher(int value) {
		super(UUID.randomUUID(), VoucherType.FIXEDAMOUNTVOUCHER, value);

		checkArgument(value >= 0 && value <= 10_000_000,
			MessageFormat.format("discountAmount는 음수이면 안됩니다. value = {0}", value));
		checkArgument(value % 100 == 0,
			MessageFormat.format("discountAmount의 최소 단위는 100원 이어야 합니다. value = {0}", value));

	}

	public FixedAmountVoucher(UUID voucherId, VoucherType type, int value) {
		super(voucherId, type, value);
	}

	/**
	 *  기존 금액에서 할인 금액을 제외한 금액을 반환하는 메서드
	 *
	 * @param beforeDiscount
	 * @return beforeDiscount - discountAmount
	 */
	public long discount(long beforeDiscount) {
		checkArgument(beforeDiscount >= 0, "beforeAmount는 양수여야 합니다. beforeAmount = {0}", beforeDiscount);

		return beforeDiscount - getValue();
	}

}
