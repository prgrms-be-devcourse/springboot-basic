package org.prgrms.kdt.model;

public class FixedAmount implements Amount {

	private final int amount;

	public FixedAmount(int amount) {
		if (!validate(amount)) {
			throw new IllegalArgumentException("FixedAmount의 할인 값은 1이상 이어야 합니다.");
		}

		this.amount = amount;
	}

	@Override
	public boolean validate(int amount) {
		if (amount < 1) {
			return false;
		}

		return true;
	}

	@Override
	public int getAmount() {
		return this.amount;
	}
}
