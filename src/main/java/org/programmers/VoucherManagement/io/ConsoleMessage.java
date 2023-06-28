package org.programmers.VoucherManagement.io;

public enum ConsoleMessage {
    START_TYPE_MESSAGE("=== Voucher Program ===\n" +
            "- 프로그램을 종료하려면 exit을 입력하세요.\n" +
            "- 새 voucher를 입력하려면 create를 입력하세요.\n" +
            "- 등록된 voucher를 조회하려면 list를 입력하세요."),
    DISCOUNT_TYPE_MESSAGE("등록할 voucher의 할인 타입을 입력하세요(percent/fixed).\n" +
            "percent : 퍼센트(%) 할인 voucher\n" +
            "fixed : 고정 금액(₩) 할인 voucher\n"),
    EXIT_MESSAGE("voucher 프로그램을 종료합니다."),
    DISCOUNT_VALUE_MESSAGE("할인 금액(% or ₩)을 입력하세요.");

    private final String message;

    ConsoleMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
