package org.prgrms.springbootbasic.engine.enumtype;

public enum ErrorCode {

    CUSTOMER_NOT_UPDATED(400, "CUSTOMER-ERR-400", "RECORD NOT UPDATED"),
    CUSTOMER_NOT_FOUND(400, "CUSTOMER-ERR-400", "RECORD NOT FOUND"),
    VOUCHER_NOT_UPDATED(400, "VOUCHER-ERR-400", "RECORD NOT UPDATED"),
    VOUCHER_NOT_FOUND(400, "VOUCHER-ERR-400", "RECORD NOT FOUND"),
    VALUE_RANGE_OUT(400, "VOUCHER-ERR-400", "OUT OF RANGE"),
    INVALID_UUID_FORMAT(400, "CUSTOMER-ERR-400", "INVALID UUID FORMAT"),
    INVALID_EMAIL_FORMAT(400, "CUSTOMER-ERR-400", "INVALID EMAIL FORMAT"),
    INVALID_VOUCHER_TYPE(400, "VOUCHER-ERR-400", "INVALID VOUCHER TYPE"),
    FIELD_NOT_BLANK(400, "COMMON-ERR-400", "FIELD NOT BLANK"),
    INTERNAL_SERVER_ERROR(500, "COMMON-ERR-500", "INTERNAL SERVER ERROR"),
    INVALID_QUERY_PARAMETER(400, "COMMON-ERR-400", "INVALID QUERY PARAMETER");


    private int Status;
    private String errorCode;
    private String message;

    ErrorCode(int status, String errorCode, String message) {
        Status = status;
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getStatus() {
        return Status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
