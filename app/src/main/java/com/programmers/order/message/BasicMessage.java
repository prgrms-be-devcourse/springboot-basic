package com.programmers.order.message;

public enum BasicMessage {

	INIT("\n=== Voucher Program ===\n Type 'exit' to exit the program.\n Type 'create' to create a new voucher.\n Type 'list' to list all vouchers.\n\n"),
	VOUCHER_SELECT("1. FixedAmountVoucher \n2. PercentDiscountVoucher \n"),
	FIX_VOUCHER_SELECT_MESSAGE("고정 할인 쿠폰 생성을 누르셨습니다.\n 할인 금액을 적어주세요. \n 할인금액[1~100_000_000] 입력 : "),
	PERCENT_VOUCHER_SELECT_MESSAGE("정률 할인 쿠폰 생성을 누르셨습니다.\n 할인율을 적어주세요.\n 할인 퍼센트[1~100] 입력 : "),
	EXIT("=== 프로그램을 종료합니다 ===\n"),
	NEW_LINE("\n");

	private String message;

	BasicMessage(String message) {
		this.message = message;
	}

	public String send() {
		return this.message;
	}

	@Override
	public String toString() {
		return "BasicMessage{" +
				"message='" + message + '\'' +
				'}';
	}
}
