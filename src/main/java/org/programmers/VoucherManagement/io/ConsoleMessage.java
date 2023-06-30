package org.programmers.VoucherManagement.io;

public enum ConsoleMessage {
    START_TYPE_MESSAGE("\n=== Voucher Program ===\n" +
            "[blacklist] - 블랙리스트에 해당하는 고객 명단을 조회하려면 blacklist를 입력하세요.\n" +
            "[exit] - 프로그램을 종료하려면 exit을 입력하세요.\n" +
            "[create] - 새 voucher를 입력하려면 create를 입력하세요.\n" +
            "[list] - 등록된 voucher를 조회하려면 list를 입력하세요."),
    DISCOUNT_TYPE_MESSAGE("등록할 voucher의 할인 타입을 입력하세요(percent/fixed).\n" +
            "[percent] : 퍼센트(%) 할인 voucher\n" +
            "[fixed] : 고정 금액(₩) 할인 voucher\n"),
    EXIT_MESSAGE("voucher 프로그램을 종료합니다."),
    DISCOUNT_VALUE_MESSAGE("할인 금액(% or ₩)을 입력하세요."),
    START_VIEW_BLACKLIST_MESSAGE("===[블랙리스트 멤버 목록]===");

    private final String message;

    ConsoleMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
