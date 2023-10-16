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
		sb.append("ListVouchersResponse { ");
		sb.append("voucherId=").append(voucherId);
		sb.append(", voucherType=").append(voucherType);

		if (voucherType == VoucherType.FIXED_AMOUNT) {
			sb.append(", amount=").append(amount);
		} else if (voucherType == VoucherType.PERCENT_DISCOUNT) {
			sb.append(", percent=").append(percent);
		}

		sb.append(" }");
		return sb.toString();
	}
}
