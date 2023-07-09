package org.programmers.VoucherManagement.member.exception;

public enum MemberExceptionMessage {
    NOT_EXIST_MEMBER_STATUS("해당하는 회원 상태가 존재하지 않습니다. ");

    private final String message;

    MemberExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
