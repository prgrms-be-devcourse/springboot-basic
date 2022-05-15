package com.programmers.order.controller.dto;

import java.util.Arrays;

public enum OrderType {
	DESC, ASC;

	static OrderType of(String orderType) {
		return Arrays.stream(OrderType.values())
				.filter(type -> type.name().toLowerCase().equals(orderType))
				.findAny()
				.orElseGet(() -> DESC);
	}

}
