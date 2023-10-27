package com.programmers.springbasic.constants;

public final class MessageConstants {
	public static final String PERCENT_PROMPT = "퍼센트 > ";
	public static final String AMOUNT_PROMPT = "금액 > ";
	public static final String NEW_AMOUNT_PROMPT = "새로운 금액 > ";
	public static final String NEW_PERCENT_PROMPT = "새로운 퍼센트 > ";
	public static final String VOUCHER_TYPE_PROMPT = "바우처 유형을 입력하세요 [fixed/percent] > ";
	public static final String CUSTOMER_ID_PROMPT = "고객 UUID > ";
	public static final String VOUCHER_ID_PROMPT = "바우처 UUID > ";
	public static final String NAME_PROMPT = "이름 > ";
	public static final String EMAIL_PROMPT = "이메일 > ";

	public static final String MAIN_MENU = """
		============== MAIN ==============
		숫자로 입력하세요
		0. 프로그램 종료
		1. 바우처 관리
		2. 고객 관리
		""";
	public static final String VOUCHER_MENU = """
		============== 바우처 관리 ==============
		0. 메인으로 돌아가기
		1. 바우처 생성
		2. 바우처 전체 목록 조회
		3. 아이디로 바우처 조회
		4. 바우처 변경 
		5. 바우처 삭제
		6. 특정 바우처를 보유한 고객 목록 조회
		""";
	public static final String CUSTOMER_MENU = """
		============== 고객 관리 ==============
		0. 메인으로 돌아가기
		1. 고객 생성 
		2. 고객 전체 목록 조회 
		3. 아이디로 고객 조회 
		4. 고객 변경 
		5. 고객 삭제
		6. 블랙리스트 고객 조회
		7. 특정 고객에게 바우처 할당
		8. 고객이 보유한 바우처 조회
		9. 고객이 보유한 바우처 삭제
		""";

	private MessageConstants() {
	}
}
