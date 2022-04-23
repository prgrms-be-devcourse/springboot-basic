package org.prgrms.springbootbasic.exception;

public class ServiceExceptionMessage {

    public static final String ALREADY_ASSIGNED_VOUCHER_EXP_MSG = "이미 할당된 바우처입니다.";
    public static final String AMOUNT_MAX_RANGE_EXP_MSG = "amount는 100000보다 작아야 합니다.";
    public static final String AMOUNT_MIN_RANGE_EXP_MSG = "amount는 0보다 작거나 같을 수 없습니다.";
    public static final String DUPLICATE_EMAIL_EXP_MSG = "이메일이 중복되었습니다.";
    public static final String INVALID_UUID_FORMAT_EXP_MSG = "UUID 포멧이 아닙니다.";
    public static final String INVALID_CUSTOMER_ID_EXP_MSG = "해당 아이디의 Customer가 존재하지 않습니다.";
    public static final String INVALID_VOUCHER_ID_EXP_MSG = "해당 아이디의 Voucher가 존재하지 않습니다.";
    public static final String PERCENT_MAX_RANGE_EXP_MSG = "percent는 100보다 작아야합니다.";
    public static final String PERCENT_MIN_RANGE_EXP_MSG = "percent는 0보다 커야합니다.";


    private ServiceExceptionMessage() {
        throw new AssertionError("생성용 클래스 아닙니다.");
    }
}
