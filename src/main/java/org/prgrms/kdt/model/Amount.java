package org.prgrms.kdt.model;

public abstract class Amount {
	private final int amount;

	public Amount(int amount) {
		if (!validate(amount)){
			throw new IllegalArgumentException("잘 못 된 할인 값입니다.");
		}
		this.amount = amount;
	}

	public abstract boolean validate(int amount);

	public int getAmount() {
		return amount;
	}
}
