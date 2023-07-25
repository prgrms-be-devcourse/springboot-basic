package org.programmers.VoucherManagement.member.exception;

public enum MemberExceptionMessage {
    NOT_EXIST_MEMBER_STATUS("해당하는 회원 상태가 존재하지 않습니다."),
    NOT_FOUND_MEMBER("회원을 찾을 수 없습니다. "),
    FAIL_TO_INSERT("데이터가 정상적으로 저장되지 않았습니다."),
    FAIL_TO_UPDATE("데이터가 정상적으로 수정되지 않았습니다."),
    FAIL_TO_DELETE("데이터가 정상적으로 삭제되지 않았습니다.");

    private final String message;

    MemberExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
