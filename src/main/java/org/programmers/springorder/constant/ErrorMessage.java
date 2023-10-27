package org.programmers.springorder.constant;

public class ErrorMessage {

    private ErrorMessage() {
    }

    public static final String VOUCHER_NOT_EXIST_MESSAGE = "등록된 바우처가 존재하지 않습니다.";
    public static final String VOUCHER_ID_NOT_EXIST_MESSAGE = "입력한 바우처 ID가 존재하지 않습니다.";
    public static final String CUSTOMER_ID_NOT_EXIST_MESSAGE = "입력한 고객 ID가 존재하지 않습니다.";

    public static final String EMPTY_VALUE_MESSAGE = "값이 존재하지 않습니다. 다시 입력해주세요.";
    public static final String INVALID_VALUE_MESSAGE = "유효하지 않은 값입니다. 다시 입력해주세요.";
    public static final String FILE_SAVE_ERROR_MESSAGE = "파일 저장 중에 문제가 발생했습니다.";
    public static final String FILE_NOT_EXIST_MESSAGE = "파일이 존재하지 않습니다.";
    public static final String FILE_FAIL_LOADING_MESSAGE = "파일에서 데이터를 가져오는 중에 문제가 발생했습니다.";
    public static final String BLACK_CONSUMER_NOT_EXIST_MESSAGE = "블랙리스트 손님이 존재하지 않습니다.";
}
