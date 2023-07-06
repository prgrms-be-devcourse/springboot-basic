package co.programmers.voucher_management.voucher.dto;

import lombok.Getter;

@Getter
public class VoucherAssignDTO {
	long customerId;
	long voucherId;

	public VoucherAssignDTO(long customerId, long voucherId) {
		this.customerId = customerId;
		this.voucherId = voucherId;
	}
}
