package com.example.voucher.io;

import java.util.HashMap;
import java.util.Map;

public enum ModeType {

	Exit("exit"),
	Create("create"),

	List("list"),
	Null("null");

	private String typeName;
	private static final Map<String, ModeType> modeTypeMap = new HashMap<>() {
		{
			put("exit", ModeType.Exit);
			put("create", ModeType.Create);
			put("list", ModeType.List);
			put("null", ModeType.Null);
		}
	};

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
