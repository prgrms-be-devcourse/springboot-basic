package com.programmers.order.domain.constraint;

public enum CustomerConstraint {
	NAME(1, 20),
	EMAIL(1, 50);

	private final int minLength;
	private final int maxLength;

	CustomerConstraint(int minLength, int maxLength) {
		this.minLength = minLength;
		this.maxLength = maxLength;
	}

	public boolean isViolate(String value) {
		int valueLength = value.length();

		return valueLength < minLength || valueLength > maxLength;
	}
}
