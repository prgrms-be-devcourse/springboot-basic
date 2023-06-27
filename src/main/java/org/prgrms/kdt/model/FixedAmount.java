package org.prgrms.kdt.model;

public class FixedAmount extends Amount {
	public FixedAmount(int amount) {
		super(amount);
	}

	@Override
	public boolean validate(int amount) {
		if (amount < 1) {
			return false;
		}

		return true;
	}
}
