package com.example.voucher.domain.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public enum VoucherType {

	FixedAmountDiscount("FixedAmount", 1),
	PercentDiscount("PercentDiscount", 2);

	private final String typeName;
	private final Integer inputNum;

	VoucherType(String typeName, Integer inputNum) {
		this.typeName = typeName;
		this.inputNum = inputNum;
	}

	public String getTypeName() {
		return typeName;
	}

	public Integer getInputNum(){
		return inputNum;
	}

	public static Optional<VoucherType> getVouchersType(Integer readVoucherType) {
		return Arrays.stream(VoucherType.values())
				.filter( e -> readVoucherType == e.getInputNum())
				.findAny();
	}

}
