package org.prgms.springbootbasic.voucher.vo;

import static com.google.common.base.Preconditions.*;

import java.text.MessageFormat;
import java.util.UUID;

import org.prgms.springbootbasic.voucher.service.VoucherType;

import com.google.common.base.Preconditions;

public class FixedAmountVoucher implements Voucher {
	private final UUID voucherId;
	private final VoucherType voucherType;
	private final long discountAmount;

	public FixedAmountVoucher(long discountAmount) {
		checkArgument(discountAmount >= 0 && discountAmount <= 10_000_000,
			MessageFormat.format("discountAmount는 음수이면 안됩니다. discountAmount = {0}", discountAmount));
		checkArgument(discountAmount % 100 == 0,
			MessageFormat.format("discountAmount의 최소 단위는 100원 이어야 합니다. discountAmount = {0}", discountAmount));

		this.voucherId = UUID.randomUUID();
		this.voucherType = VoucherType.FIXAMOUNTVOUCHER;
		this.discountAmount = discountAmount;
	}

	public FixedAmountVoucher(UUID voucherId, long discountAmount) {
		this.voucherId = voucherId;
		this.voucherType = VoucherType.FIXAMOUNTVOUCHER;
		this.discountAmount = discountAmount;
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
		return this.discountAmount;
	}

	/**
	 *  기존 금액에서 할인 금액을 제외한 금액을 반환하는 메서드
	 *
	 * @param beforeDiscount
	 * @return beforeDiscount - discountAmount
	 */
	@Override
	public long discount(long beforeDiscount) {
		checkArgument(beforeDiscount >= 0, "beforeAmount는 양수여야 합니다. beforeAmount = {0}", beforeDiscount);

		return beforeDiscount - discountAmount;
	}

	@Override
	public String toString() {
		return "FixedAmountVoucher{" +
			"voucherId=" + voucherId +
			", discountAmount=" + discountAmount +
			'}';
	}
}
