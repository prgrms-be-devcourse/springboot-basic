package org.prgrms.kdt.model;

public abstract class Amount {
	private final int amount;

	public Amount(int amount) {
		this.amount = amount;
	}

	public boolean validate(int amount) {
		return true;
	}

	public int getAmount() {
		return amount;
	}
}
