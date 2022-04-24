package org.prgms.springbootbasic.voucher.vo;

import static com.google.common.base.Preconditions.*;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
	private final UUID voucherId;
	private final VoucherType voucherType;
	private final int discountPercent;

	public PercentDiscountVoucher(int discountPercent) {
		checkArgument(discountPercent >= 0 && discountPercent <= 100
			, "discountPercent(할인율)은 0 ~ 100 사이의 정수여야 합니다. discountPercent = {0}", discountPercent);

		this.voucherId = UUID.randomUUID();
		this.voucherType = VoucherType.PERCENTDISCOUNTVOUCHER;
		this.discountPercent = discountPercent;
	}

	public PercentDiscountVoucher(UUID voucherId, int value) {
		this.voucherId = voucherId;
		this.voucherType = VoucherType.PERCENTDISCOUNTVOUCHER;
		this.discountPercent = value;
	}

	@Override
	public UUID getVoucherId() {
		return this.voucherId;
	}

	@Override
	public VoucherType getVoucherType() {
		return this.voucherType;
	}

	@Override
	public long getValue() {
		return this.discountPercent;
	}

	/**
	 * 기존 가격에서 할인율을 곱한 가격을 제외한 나머지 가격을 반환한다.
	 *
	 * @param beforeDiscount
	 * @return beforeDiscount - beforeDiscount * discountPercent / 100
	 */
	@Override
	public long discount(long beforeDiscount) {
		checkArgument(beforeDiscount >= 0, "beforeDiscount는 음수이면 안됩니다. beforeDiscount = {0}", beforeDiscount);

		return beforeDiscount - beforeDiscount * discountPercent / 100;
	}

	@Override
	public String toString() {
		return "PercentDiscountVoucher{" +
			"voucherId=" + voucherId +
			", discountPercent=" + discountPercent +
			'}';
	}
}
