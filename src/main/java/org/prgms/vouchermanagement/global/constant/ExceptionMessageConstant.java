package org.prgms.vouchermanagement.global.constant;

public final class ExceptionMessageConstant {
    private ExceptionMessageConstant() {}

    public static final String COMMAND_INPUT_EXCEPTION = "[ERROR] 지원되지 않는 Command 입니다.";
    public static final String VOUCHER_TYPE_INPUT_EXCEPTION = "[ERROR] 1 또는 2를 입력해주세요.";
    public static final String FIXED_VOUCHER_AMOUNT_INPUT_EXCEPTION = "[ERROR] 올바른 Amount 값이 아닙니다.";
    public static final String PERCENT_DISCOUNT_INPUT_EXCEPTION = "[ERROR] 올바른 Percent 값이 아닙니다.";
    public static final String NO_BLACK_LIST_FILE_EXCEPTION = "[ERROR] 블랙 리스트 파일이 존재하지 않습니다.";

    public static final String EMPTY_CUSTOMER_INSERT_EXCEPTION = "[ERROR] 고객정보가 저장되지 않았습니다.";
    public static final String BLANK_CUSTOMER_NAME_EXCEPTION = "[ERROR] 이름을 입력해주세요.";
    
    public static final String VOUCHER_NOT_INSERTED_EXCEPTION = "[ERROR] 바우처가 저장되지 않았습니다.";
}
