package org.prgrms.voucherapplication.exception;

/**
 * 에러 종류를 정의한 enum class
 * INVALID_MENU: 잘못된 메뉴 입력 시 발생
 * INVALID_VOUCHER_TYPE: 잘못된 바우처 타입 입력 시 발생
 * NO_SUCH_VOUCHER: 잘못된 바우처 id로 바우처를 찾는 경우 발생
 * DATA_NOT_INSERTED: mysql에 데이터 삽입이 실패했을 경우 발생
 * DATA_NOT_UPDATED: mysql에 데이터 업데이트가 실패했을 경우 발생
 * INVALID_CUSTOMER_INFORMATION_TYPE: 고객 정보 타입(id, name, email)외에 다른 문자열을 입력했을 경우 발생
 */
public enum ErrorType {
    INVALID_MENU("Invalid menu"),
    INVALID_VOUCHER_TYPE("Invalid voucher type"),
    NO_SUCH_VOUCHER("No such voucher."),
    DATA_NOT_INSERTED("Data is not inserted"),
    DATA_NOT_UPDATED("Data is not updated"),
    INVALID_CUSTOMER_INFORMATION_TYPE("Invalid customer information type");

    private final String message;

    ErrorType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
