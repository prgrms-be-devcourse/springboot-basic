package com.programmers.kwonjoosung.springbootbasicjoosung.console.message;

public enum CustomerResponseMessage implements ResponseMessage {
    CREATE_CUSTOMER_FAIL("Customer 생성에 실패했습니다."),
    UPDATE_CUSTOMER_SUCCESS("해당 Customer 정보를 수정했습니다."),
    DELETE_CUSTOMER_SUCCESS("해당 Customer를 삭제했습니다."),
    NOT_FOUND_CUSTOMER_ERROR("해당하는 ID를 가진 Customer가 없습니다."),
    NOT_YET_CUSTOMER_SAVE_ERROR("아직 등록된 Customer가 없습니다."),
    NOT_FOUND_OWNED_VOUCHER("소유한 Voucher를 찾지 못했습니다.");
    private final String message;

    CustomerResponseMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
