package org.programmers.kdt.voucher;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoucherDto {
	private final VoucherType voucherType;
	private final long discountAmount;

	public VoucherDto(String voucherType, long discountAmount) {
		this.voucherType = VoucherType.of(voucherType);
		this.discountAmount = discountAmount;
	}
}
