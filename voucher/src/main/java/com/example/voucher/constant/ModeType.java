package com.example.voucher.constant;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum ModeType {

	Exit("exit"),
	Create("create"),
	List("list");

	private String typeName;

	ModeType(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}

	public static ModeType getTypeMode(String typeName) {
		return Arrays.stream(ModeType.values())
					.filter(e->typeName.equals(e.getTypeName()))
					.findAny()
					.orElseThrow(() -> new NoSuchElementException(ConstantStrings.MESSAGE_PRINT_RETRY_VOUCHER_TYPE_SELECTION_PROMPT));
	}

}
