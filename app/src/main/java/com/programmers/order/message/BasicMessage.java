package com.programmers.order.message;

public enum BasicMessage {

	PROGRAME_INIT("\n번호를 선택하세요.\n1. 고객 관리 프로그램 \n2. 쿠폰 관리 프로그램\n3. 프로그램 종료\n\n 입력 : "),

	CUSTOMER_INIT(
			"\n=== Customer Program ===\n Type 'exit' to exit the program.\n Type 'create' to create a new customer.\n Type 'register' to customer mapping voucher.\n Type 'list_up_with_voucher' to list up voucher of customer.\n Type 'unmapping' to delete voucher of customer. \n\n 입력 : "),

	CUSTOMER_CREATE("\n이름과 이메일을 입력해주세요. (ex: 홍길동,dong@programmers.co.kr)\n\n 입력 : "),
	CUSTOMER_REGISTER_COUPON("\n이메일과 쿠폰 번호를 입력해주세요. (ex: 홍길동,123-3123-123)\n\n 입력 : "),

	CUSTOMER_LIST_UP_WITH_VOUCHER("\n이메일을 입력해주세요. 쿠폰 보유 항목을 조회힙니다.\n\n 입력 : "),
	CUSTOMER_UN_MAPPING("\n이메일과 쿠폰 번호를 입력해주세요. 보유한 쿠폰을 삭제합니다.\n\n 입력 : "),
	VOUCHER_INIT(
			"\n=== Voucher Program ===\n Type 'exit' to exit the program.\n Type 'list_up_with_customer' to exit the program.\n Type 'create' to create a new voucher.\n Type 'list' to list all vouchers.\n\n 입력 : "),
	VOUCHER_SELECT("\n번호를 선택하세요.\n1. FixedAmountVoucher \n2. PercentDiscountVoucher \n\n 입력 : "),
	FIX_VOUCHER_SELECT_MESSAGE("\n고정 할인 쿠폰 생성을 누르셨습니다.\n할인 금액을 적어주세요.\n할인금액[1~100_000_000]\n\n 입력 : "),
	PERCENT_VOUCHER_SELECT_MESSAGE("\n정률 할인 쿠폰 생성을 누르셨습니다.\n할인율을 적어주세요.\n할인 퍼센트[1~100] \n\n 입력 : "),
	VOUCHER_LIST_UP_WITH_CUSTOMER("\n쿠폰 번호를 입력해주세요. 쿠폰을 보유한 고객들을 조회힙니다.\n\n 입력 : "),
	EXIT("\n=== 프로그램을 종료합니다 ===\n"),
	NOT_EXIST_DATE("========= 데이터가 존재하지 않습니다..========="),
	NEW_LINE("\n"), CUSTOMER_REGISTER_COMPLETE("등록이 완료되었습니다.\n\n"),
	CUSTOMER_NOT_EXIST_EMAIL("조회되지 않는 이메일 입니다. 다시 입력해주세요. \n\n");

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
