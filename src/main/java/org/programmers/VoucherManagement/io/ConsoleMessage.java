package org.programmers.VoucherManagement.io;

public enum ConsoleMessage {
    START_TYPE_MESSAGE("===Member===\n" +
            "<< 1 >> [insertMember] - 멤버를 추가하려면 1를 입력하세요.\n" +
            "<< 2 >> [updateMember] - 멤버를 수정하려면 2를 입력하세요.\n" +
            "<< 3 >> [deleteMember] - 특정 고객을 삭제하려면 3을 입력하세요.\n" +
            "<< 4 >> [blackMemberList] - 블랙리스트에 해당하는 고객 명단을 조회하려면 4를 입력하세요.\n" +
            "<< 5 >> [memberList] - 전체 고객 명단을 조회하려면 5를 입력하세요.\n" +
            "\n" +
            "===Voucher===\n" +
            "[insertVoucher] - 새 voucher를 추가하려면 insertVoucher를 입력하세요.\n" +
            "[updateVoucher] - 바우처 정보를 수정하려면 updateVoucher를 입력하세요. \n" +
            "[findVoucher] - 바우처 쿠폰번호를 이용해 바우처 정보를 검색하려면 findVoucher를 입력하세요.\n" +
            "[voucherList] - 전체 voucher 명단을 조회하려면 voucherList를 입력하세요.\n" +
            "\n" +
            "===System===\n" +
            "[exit] -프로그램을 종료하려면 exit을 입력하세요.\n"),

    DISCOUNT_TYPE_MESSAGE("등록할 voucher의 할인 타입을 입력하세요(percent/fixed).\n" +
            "[percent] : 퍼센트(%) 할인 voucher\n" +
            "[fixed] : 고정 금액(₩) 할인 voucher\n"),
    DISCOUNT_VALUE_MESSAGE("할인 금액(% or ₩)을 입력하세요."),
    MEMBER_STATUS_MESSAGE("멤버의 회원 상태(BLACK / WHITE)를 입력하세요."),
    MEMBER_ID_MESSAGE("수정할 회원의 ID를 입력하세요."),
    MEMBER_NAME_MESSAGE("추가할 멤버의 이름을 입력하세요."),
    START_VIEW_BLACKLIST_MESSAGE("===[블랙리스트 멤버 목록]==="),
    START_VIEW_ALL_MEMBER_MESSAGE("===[전체 멤버 목록]==="),
    EXIT_MESSAGE("voucher 프로그램을 종료합니다."),
    TASK_SUCCESSFUL_MESSAGE("요청을 성공적으로 처리했습니다.");


    private final String message;

    ConsoleMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
