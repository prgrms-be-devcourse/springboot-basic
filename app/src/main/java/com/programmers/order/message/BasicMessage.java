package com.programmers.order.message;

public enum BasicMessage {

	PROGRAME_INIT("\n번호를 선택하세요.\n1. 고객 관리 프로그램 \n2. 쿠폰 관리 프로그램\n3. 프로그램 종료\n\n 입력 : "),

	CUSTOMER_INIT("\n=== Customer Program ===\n Type 'exit' to exit the program.\n Type 'create' to create a new customer.\n Type 'voucher-register' to list all customer.\n Type 'list' to list up voucher of customer.\n Type 'delete' to delete voucher of customer.\n Type 'voucher' to list up customer of voucher. \n\n 입력 : "),
	VOUCHER_INIT("\n=== Voucher Program ===\n Type 'exit' to exit the program.\n Type 'create' to create a new voucher.\n Type 'list' to list all vouchers.\n\n 입력 : "),
	VOUCHER_SELECT("\n번호를 선택하세요.\n1. FixedAmountVoucher \n2. PercentDiscountVoucher \n\n 입력 : "),
	FIX_VOUCHER_SELECT_MESSAGE("\n고정 할인 쿠폰 생성을 누르셨습니다.\n할인 금액을 적어주세요.\n할인금액[1~100_000_000]\n\n 입력 : "),
	PERCENT_VOUCHER_SELECT_MESSAGE("\n정률 할인 쿠폰 생성을 누르셨습니다.\n할인율을 적어주세요.\n할인 퍼센트[1~100] \n\n 입력 : "),
	EXIT("\n=== 프로그램을 종료합니다 ===\n"),
	NOT_EXIST_DATE("========= 데이터가 존재하지 않습니다..========="),
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
