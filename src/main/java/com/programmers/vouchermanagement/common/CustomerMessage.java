package com.programmers.vouchermanagement.common;

public enum CustomerMessage {
    CUSTOMER_COMMAND_LIST_MESSAGE("""      
            === Customer Management ===
            0. 뒤로 가기
            1. 전체 목록
            2. ID로 검색
            3. 이름으로 검색
            4. 고객 블랙리스트 조회
            5. 고객 생성
            6. 고객 삭제
            """),

    // Input messages
    INPUT_CUSTOMER_ID_MESSAGE("Enter customer id."),
    INPUT_CUSTOMER_NAME_MESSAGE("Enter customer name."),
    CUSTOMER_CREATED_MESSAGE("[System] Customer created."),
    CUSTOMER_DELETED_MESSAGE("[System] Customer deleted.");

    private final String message;

    CustomerMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
