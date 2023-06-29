package com.example.voucher.io;

import java.util.Map;

public enum ModeType {

	Exit("exit"),
	Create("create"),
	List("list"),
	Null("null");

	private String typeName;

	private static final Map<String, ModeType> modeTypeMap;

	static {
		modeTypeMap = Map.of("exit", ModeType.Exit, "create", ModeType.Create, "list", ModeType.List, "null",
			ModeType.Null);
	}

	ModeType(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}

	public static ModeType getTypeMode(String typeName) {
		return modeTypeMap.getOrDefault(typeName, ModeType.Null);
	}

}
