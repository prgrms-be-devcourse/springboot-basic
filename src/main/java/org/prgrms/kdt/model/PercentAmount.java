package org.prgrms.kdt.model;

public class PercentAmount extends Amount {

	public PercentAmount(int amount) {
		super(amount);
	}

	@Override
	public boolean validate(int amount) {
		if (amount <= 0 || amount > 100 ){
			return false;
		}

		return true;
	}
}
