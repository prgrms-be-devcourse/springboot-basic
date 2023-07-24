package org.prgrms.kdt.common.codes;

public enum ErrorCode {
	VOUCHER_ID_NOT_FOUND(10001, "존재하지 않는 Voucher Id 조회"),
	VOUCHER_DELETE_FAIL(10002, "Voucher 객체 삭제 실패"),
	VOUCHER_CREATE_FAIL(10003, "Voucher 생성 실패"),
	VOUCHER_UPDATE_FAIL(10004, "Voucher 수정 실패"),
	CUSTOMER_ID_NOT_FOUND(20001, "존재하지 않은 Customer Id 조회"),
	CUSTOMER_DELETE_FAIL(20002, "Customer 객체 삭제 실패"),
	CUSTOMER_CREATE_FAIL(20003, "Customer 생성 실패"),
	CUSTOMER_UPDATE_FAIL(20004, "Customer 수정 실패");

	private final String errorMessage;

	private final int errorNumber;


	ErrorCode( int errorNumber, String errorMessage) {
		this.errorMessage = errorMessage;
		this.errorNumber = errorNumber;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public int getErrorNumber() {
		return errorNumber;
	}
}
