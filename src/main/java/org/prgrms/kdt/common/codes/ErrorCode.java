package org.prgrms.kdt.common.codes;

public enum ErrorCode {

	VOUCHER_ID_NOT_FOUND(10001, "존재하지 않는 Voucher Id 조회"),
	VOUCHER_DELETE_FAIL(10002, "Voucher 객체 삭제 실패"),
	CUSTOMER_ID_NOT_FOUND(20001, "존재하지 않은 Customer Id 조회"),
	CUSTOMER_DELETE_FAIL(20002, "Customer 객체 삭제 실패");

	private final String errorMessage;

	private final int errorCode;


	ErrorCode( int errorCode, String errorMessage) {
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
