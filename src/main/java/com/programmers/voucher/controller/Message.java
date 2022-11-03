package com.programmers.voucher.controller;

public enum Message {

	COMMAND_OPTION(
		"=== Voucher Program ===" + System.lineSeparator()
		+ "Type exit to exit the program." + System.lineSeparator()
		+ "Type create to create a new voucher." + System.lineSeparator()
		+ "Type list to list all vouchers."),
	VOUCHER_OPTION(
		"고정 할인 바우처를 생성하려면 FixedDiscountVoucher 를 입력해주세요." + System.lineSeparator()
		+ "비율 할인 바우처를 생성하려면 PercentDiscountVoucher 를 입력해주세요."),
	DISCOUNT_OPTION("할인 정도를 적어주세요.");

	private String message;

	Message(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
