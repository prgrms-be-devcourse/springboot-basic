package com.programmers.springbasic.dto;

import java.util.UUID;

import com.programmers.springbasic.entity.voucher.Voucher;
import com.programmers.springbasic.entity.voucher.VoucherType;

public class ListVouchersResponse {
	private final UUID voucherId;
	private final VoucherType voucherType;
	private final long amount;
	private final long percent;

	public ListVouchersResponse(Voucher voucher) {
		this.voucherId = voucher.getVoucherId();
		this.voucherType = voucher.getVoucherType();
		this.amount = voucher.getAmount();
		this.percent = voucher.getPercent();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ 바우처 아이디 = ").append(voucherId);
		sb.append(", 바우처 타입 = ").append(voucherType);

		if (voucherType == VoucherType.FIXED_AMOUNT) {
			sb.append(", 할인 금액 = ").append(amount);
		} else if (voucherType == VoucherType.PERCENT_DISCOUNT) {
			sb.append(", 할인율 = ").append(percent);
		}
		sb.append(" ]");
		return sb.toString();
	}
}
