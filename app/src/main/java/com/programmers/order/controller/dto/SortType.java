package com.programmers.order.controller.dto;

import java.util.Arrays;

public enum SortType {
	CREATED("created_at"),
	UPDATED("updated_at"),
	EXPIRATION("expiration_at");

	private final String value;

	SortType(String value) {
		this.value = value;
	}

	static SortType of(String sortType) {
		return Arrays.stream(SortType.values())
				.filter(sort -> sort.value.equals(sortType))
				.findAny().orElseGet(() -> CREATED);
	}
}
