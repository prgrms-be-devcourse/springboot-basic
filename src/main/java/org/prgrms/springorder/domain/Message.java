package org.prgrms.springorder.domain;

public enum Message {
	MENU_MESSAGE("""
		=== Voucher Program ===
		Type exit to exit the program.
		Type create to create a new voucher.
		Type join to create a new customer.
		Type list to list all vouchers.
		Type blacklist to list blacklist
		Type allocate to allocate voucher to customer
		Type get to get customer's vouchers
		Type delete to delete customer voucher
		Type search to search by voucher
		"""),
	SELECT_VOUCHER_MESSAGE("""
		Write 1 to make fixedAmountVoucher
		Write 2 to make percentDiscountVoucher
		"""),
	FIXED_AMOUNT_MESSAGE("Amount should be over 0 : "),
	PERCENT_DISCOUNT_MESSAGE("Percentage should be over 0 and less than 100 : "),
	CUSTOMER_ID_MESSAGE("Write customer ID : "),
	VOUCHER_ID_MESSAGE("Write voucher ID : "),
	REQUEST_NAME_MESSAGE("Write your name"),
	REQUEST_EMAIL_MESSAGE("Write your email");

	private final String message;

	Message(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}
}
