package com.programmers.order.message;

public enum InfoLogMessage {
	INFO_PREFIX("info : {}"),
	DUPLICATE_VOUCHER_REGISTER("쿠폰 등록을 재시도 하였습니다."),

	POSSIBLE_REGISTER("쿠폰 등록이 가능한 회원 입니다.");

	private final String message;

	InfoLogMessage(String message) {
		this.message = message;
	}

	public static String getPrefix() {
		return INFO_PREFIX.message;
	}

	@Override
	public String toString() {
		return "InfoLogMessage{" +
				"message='" + message + '\'' +
				'}';
	}
}
