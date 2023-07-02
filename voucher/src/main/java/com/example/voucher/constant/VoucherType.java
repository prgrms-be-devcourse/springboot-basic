package com.example.voucher.constant;

import java.util.Arrays;
import java.util.NoSuchElementException;

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

	public static VoucherType getVouchersType(Integer readVoucherType) {
		return Arrays.stream(VoucherType.values())
				.filter( e -> readVoucherType == e.getInputNum())
				.findAny()
				.orElseThrow(() -> new NoSuchElementException(ConstantStrings.MESSAGE_PRINT_RETRY_VOUCHER_TYPE_SELECTION_PROMPT));
	}

}
