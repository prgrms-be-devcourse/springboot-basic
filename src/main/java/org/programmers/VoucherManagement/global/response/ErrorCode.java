package org.programmers.VoucherManagement.global.response;

public enum ErrorCode {
    /**
     * 10000번 -> Global에서 발생하는 에러코드 관리
     * [Http Status code]
     * 400 : Bad Request
     * 401 : Unauthorized
     * 403 : Forbidden
     * 404 : Not Found
     * 405 : Method Not Allowed
     * 500 : Internal Server Error
     */
    FAIL(500, "10000", "요청에 실패하였습니다."),
    INVALID_INPUT_VALUE_ERROR(400, "10001", "유효하지 않은 입력값입니다."),
    INVALID_METHOD_ERROR(405, "10002", "Method Argument가 적절하지 않습니다."),
    REQUEST_BODY_MISSING_ERROR(400, "10003", "RequestBody에 데이터가 존재하지 않습니다."),
    REQUEST_PARAM_MISSING_ERROR(400, "10004", "RequestParam에 데이터가 전달되지 않았습니다."),
    INVALID_TYPE_VALUE_ERROR(400, "10005", "타입이 유효하지 않습니다."),
    INTERNAL_SERVER_ERROR(500, "10006", "서버 오류 입니다."),

    /**
     * ==========================================================================
     * 20000번 -> Member에서 발생하는 에러코드 관리
     * ==========================================================================
     */
    NOT_EXIST_MEMBER_STATUS(400, "M20000", "해당하는 회원 상태가 존재하지 않습니다."),
    NOT_FOUND_MEMBER(404, "M20001", "회원을 찾을 수 없습니다."),
    FAIL_TO_INSERT_MEMBER(500, "M20002", "데이터가 정상적으로 저장되지 않았습니다."),
    FAIL_TO_UPDATE_MEMBER(500, "M20003", "데이터가 정상적으로 수정되지 않았습니다."),
    FAIL_TO_DELETE_MEMBER(500, "M20004", "데이터가 정상적으로 삭제되지 않았습니다."),

    /**
     * ==========================================================================
     * 30000번 -> Voucher에서 발생하는 에러코드 관리
     * ==========================================================================
     */
    NOT_INCLUDE_1_TO_100(400, "V30000", "할인율은 1부터 100사이의 값이여야 합니다."),
    AMOUNT_IS_NOT_NUMBER(400, "V30001", "숫자만 입력가능합니다."),
    NOT_EXIST_COMMAND(400, "V30002", "해당하는 Command가 존재하지 않습니다."),
    NOT_EXIST_DISCOUNT_TYPE(400, "V30003", "해당하는 유형의 바우처가 존재하지 않습니다."),
    NOT_FOUND_VOUCHER(404, "V30004", "바우처를 찾을 수 없습니다."),
    FAIL_TO_INSERT_VOUCHER(500, "V30005", "데이터가 정상적으로 저장되지 않았습니다."),
    FAIL_TO_UPDATE_VOUCHER(500, "V30006", "데이터가 정상적으로 수정되지 않았습니다."),
    FAIL_TO_DELETE_VOUCHER(500, "V30007", "데이터가 정상적으로 삭제되지 않았습니다."),

    /**
     * ==========================================================================
     * 40000번 -> Wallet에서 발생하는 에러코드 관리
     * ==========================================================================
     */
    FAIL_TO_INSERT_WALLET(500, "W40000", "데이터가 정상적으로 저장되지 않았습니다."),
    FAIL_TO_DELETE_WALLET(500, "W40001", "데이터가 정상적으로 삭제되지 않았습니다.");


    private final int status; //코드 상태(Http)
    private final String divisionCode; //서버 내 코드 구분 값
    private final String message; //에러 코드 메시지

    ErrorCode(int status, String divisionCode, String message) {
        this.status = status;
        this.divisionCode = divisionCode;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public String getDivisionCode() {
        return divisionCode;
    }

}
