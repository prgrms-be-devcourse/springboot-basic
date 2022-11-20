package com.programmers.voucher.controller;

public enum Message {

	COMMAND_OPTION("""
				
		=== Voucher Program ===
		Type \"create voucher\" to create a new voucher.
		Type \"create customer\" to create a new customer.
		Type \"get all voucher\" to list all vouchers.
		Type \"get all customer\" to list all customers.
		Type \"get all blacklist\" to list all blacklists
		Type \"exit\" to exit the program."""),
	VOUCHER_OPTION("""
		  
		고정 할인 바우처를 생성하려면 fixed 를 입력해주세요.
		비율 할인 바우처를 생성하려면 percent 를 입력해주세요."""),
	DISCOUNT_OPTION("할인 정도를 적어주세요."),
	CUSTOMER_OPTION("""
		  
		NORMAL 고객을 생성하려면 normal 을 입력해주세요.
		BLACKLIST 고객을 생성하려면 blacklist 를 입력해주세요.""");

	private final String message;

	Message(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
