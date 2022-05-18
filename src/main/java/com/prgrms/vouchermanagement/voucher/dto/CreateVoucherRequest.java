package com.prgrms.vouchermanagement.voucher.dto;

import com.prgrms.vouchermanagement.voucher.VoucherType;

public class CreateVoucherRequest {
	private VoucherType type;
	private long discountInfo;

	public VoucherType getType() {
		return type;
	}

	public long getDiscountInfo() {
		return discountInfo;
	}

}
