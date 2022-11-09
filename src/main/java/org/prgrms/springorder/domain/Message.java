package org.prgrms.springorder.domain;

public enum Message {
	FIXED_AMOUNT_MESSAGE("Amount should be over 0"),
	PERCENT_DISCOUNT_MESSAGE("Percentage should be over 0 and less than 100");

	private final String context;

	Message(String context) {
		this.context = context;
	}

	@Override
	public String toString() {
		return context;
	}
}
