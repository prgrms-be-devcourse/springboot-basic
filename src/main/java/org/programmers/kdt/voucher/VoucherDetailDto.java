package org.programmers.kdt.voucher;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoucherDetailDto {
	private String voucherId = "----";
	private String voucherType = VoucherType.NONE.toString();
	private long discountAmount = 0;
	private String status = VoucherStatus.INVALID.toString();
	private String ownerId = "----";

	public static VoucherDetailDto empty() {
		return new VoucherDetailDto();
	}

	public void updateOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
}
