package com.programmers.springbasic.constants;

public final class MessageConstants {
	public static final String PERCENT_PROMPT = "Percent : ";
	public static final String AMOUNT_PROMPT = "Amount : ";
	public static final String NEW_AMOUNT_PROMPT = "New Amount : ";
	public static final String NEW_PERCENT_PROMPT = "New Percent : ";
	public static final String VOUCHER_TYPE_PROMPT = "Choose voucher type : [fixed/percent] => ";
	public static final String CUSTOMER_ID_PROMPT = "Customer UUID : ";
	public static final String VOUCHER_ID_PROMPT = "Voucher UUID : ";
	public static final String NAME_PROMPT = "name : ";
	public static final String EMAIL_PROMPT = "email : ";

	public static final String MAIN_MENU = """
		=== Voucher Program ===
		voucher
		customer 
		""";
	public static final String VOUCHER_MENU = """
		=== Voucher Program ===
		return to main
		1. create voucher 2. list vouchers 3. get voucher 4. update voucher 5. delete voucher
		""";
	public static final String CUSTOMER_MENU = """
		=== Voucher Program ===
		return to main
		6. create customer 7.list customers 8. blacklist 9. get customer detail 10. update customer 11. delete customer
		""";

	private MessageConstants() {
	}
}
