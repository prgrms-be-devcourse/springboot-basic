package com.example.voucher.domain.enums;

import java.util.HashMap;
import java.util.Map;

import com.example.voucher.io.ModeType;

public enum VoucherType {
	FixedAmount("FixedAmount"),
	PercentDiscount("PercentDiscount"),
	Null("Null");

	private final String typeName;

	private static final Map<Integer, VoucherType> voucherTypeMap = new HashMap<>() {
		{
			put(1, VoucherType.FixedAmount);
			put(2, VoucherType.PercentDiscount);
		}
	};

	VoucherType(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}

	public static VoucherType getVouchersType(Integer readVoucherType) {
		return voucherTypeMap.getOrDefault(readVoucherType, VoucherType.Null);
	}

}
