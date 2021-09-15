package org.programmers.kdt.voucher;

import lombok.Data;

@Data
public class VoucherDto {
	private final VoucherType voucherType;
	private final long discountAmount;

	public VoucherDto(String voucherType, long discountAmount) {
		this.voucherType = VoucherType.of(voucherType);
		this.discountAmount = discountAmount;
	}
}
