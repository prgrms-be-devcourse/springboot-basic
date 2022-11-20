package com.programmers.voucher.controller;

public enum Message {

	COMMAND_OPTION("""
				
		=== Voucher Program ===
		Type \"create\" to create a new voucher.
		Type \"register\" to register a new customer.
		Type \"allocate\" to allocate a voucher to customer.
		Type \"remove\" to remove a customer's voucher. 
		Type \"vouchers_by_customer\" to search vouchers of a specific customer.
		Type \"customers_by_voucher\" to search for customers with a specific voucher.
		Type \"vouchers\" to list all vouchers.
		Type \"customers\" to list all customers.
		Type \"blacklists\" to list all blacklists
		Type \"exit\" to exit the program."""),
	VOUCHER_OPTION("""
		  
		고정 할인 바우처를 생성하려면 fixed 를 입력해주세요.
		비율 할인 바우처를 생성하려면 percent 를 입력해주세요."""),
	DISCOUNT_OPTION("할인 정도를 적어주세요."),
	VOUCHER_SELECT_OPTION("원하시는 바우처의 아이디를 입력해주세요."),
	CUSTOMER_OPTION("""
		  
		NORMAL 고객을 생성하려면 normal 을 입력해주세요.
		BLACKLIST 고객을 생성하려면 blacklist 를 입력해주세요."""),
	CUSTOMER_SELECT_OPTION("원하시는 고객의 아이디를 입력해주세요.");

	private final String message;

	Message(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
