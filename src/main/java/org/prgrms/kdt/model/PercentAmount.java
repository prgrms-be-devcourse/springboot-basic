package org.prgrms.kdt.model;

public class PercentAmount implements Amount {

	private final int amount;

	public PercentAmount(int amount) {
		if (!validate(amount)) {
			throw new IllegalArgumentException("PercentAmount의 할인 값은 1이상 100이하 이어야 합니다.");
		}

		this.amount = amount;
	}

	@Override
	public boolean validate(int amount) {
		return amount > 0 && amount <= 100;
	}

	@Override
	public int getAmount() {
		return this.amount;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		PercentAmount that = (PercentAmount)o;

		return amount == that.amount;
	}

	@Override
	public int hashCode() {
		return amount;
	}
}
