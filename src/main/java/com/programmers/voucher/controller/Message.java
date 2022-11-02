package com.programmers.voucher.controller;

public enum Message {

	COMMAND_OPTION("=== Voucher Program ===" + System.lineSeparator()
		+ "Type exit to exit the program." + System.lineSeparator()
		+ "Type create to create a new voucher." + System.lineSeparator()
		+ "Type list to list all vouchers."),
	VOUCHER_OPTION("생성하고 싶은 바우처를 선택해주세요."),
	DISCOUNT_OPTION("할인 정도를 적어주세요.");

	private String message;

	Message(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
