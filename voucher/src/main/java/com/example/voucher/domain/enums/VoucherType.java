package com.example.voucher.domain.enums;

public enum VoucherType {
	FixedAmount("FixedAmount"),
	PercentDiscount("PercentDiscount");

	private final String typeName;

	VoucherType(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName(){
		return typeName;
	}
}
