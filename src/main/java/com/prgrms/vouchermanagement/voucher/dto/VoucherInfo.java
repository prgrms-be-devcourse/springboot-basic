package com.prgrms.vouchermanagement.voucher.dto;

import java.util.UUID;

import com.prgrms.vouchermanagement.voucher.VoucherType;
import com.prgrms.vouchermanagement.voucher.domain.Voucher;

public class VoucherInfo {
	private final UUID id;
	private final VoucherType type;
	private final long discountInfo;

	public VoucherInfo(UUID id, VoucherType type, long discountInfo) {
		this.id = id;
		this.type = type;
		this.discountInfo = discountInfo;
	}

	@Override
	public String toString() {
		return "VoucherInfo{" +
			"id=" + id +
			", type=" + type +
			", discountInfo=" + discountInfo +
			'}';
	}

	public static VoucherInfo fromEntity(Voucher voucher) {
		return new VoucherInfo(voucher.getVoucherId(), voucher.getType(), voucher.getDiscountInfo());
	}

	public UUID getId() {
		return id;
	}

	public VoucherType getType() {
		return type;
	}

	public long getDiscountInfo() {
		return discountInfo;
	}
}
