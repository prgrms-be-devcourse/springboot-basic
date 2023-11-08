package org.prgrms.kdt.customer;

public enum CustomerMessage {
    EXCEPTION_NOT_EXIST_CUSTOMER("존재하지 않는 회원입니다."),
    GET_HAVE_VOUCHERS("가지고있는 바우처를 모두 조회할 고객의 아이디를 입력해주세요 : "),
    CREATE_CUSTOMER_NAME("생성할 회원의 이름을 입력해주세요 : "),
    CREATE_CUSTOMER_EMAIL("생성할 회원의 이메일을 입력해주세요 : ");

    private final String message;

    public String getMessage() {
        return message;
    }

    CustomerMessage(String message) {
        this.message = message;
    }
}
