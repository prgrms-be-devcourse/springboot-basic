package com.example.voucher.io;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

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

	public static Optional<ModeType> getTypeMode(String typeName) {
		return Arrays.stream(ModeType.values())
					.filter(e->typeName.equals(e.getTypeName()))
					.findAny();
	}

}
