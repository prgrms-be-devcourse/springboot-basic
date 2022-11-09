package org.prgrms.springorder.domain;

public enum Message {
	MENU_MESSAGE("""
		=== Voucher Program ===
		Type exit to exit the program.
		Type create to create a new voucher.
		Type list to list all vouchers.
		Type blacklist to list blacklist
		"""),
	SELECT_VOUCHER_MESSAGE("""
		Write 1 to make fixedAmountVoucher
		Write 2 to make percentDiscountVoucher
		"""),
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
