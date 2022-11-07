package com.programmers.commandLine.global.io;

import org.springframework.stereotype.Component;

public enum Message {
    SELECT_MENU("""                
            === 바우처 프로그램 ===
            프로그램을 종료하려면 exit를 입력하십시오.
            voucher_create를 입력하여 새 바우처를 만드십시오.
            모든 바우처를 나열하려면 voucher_list를 입력하십시오.
            모든 악덕 소비자를 나열하려면 black_consumer_list를 입력하십시오.
                            
            === Voucher Program ===
            Type exit to exit the program.
            Type voucher_create to create a new voucher.
            Type voucher_list to list all vouchers.
            Type black_consumer_list to list all blackConsumer.

            입력 예시 : exit, voucher_create, voucher_list, black_consumer_list
            입력: """),

    MENU_ERROR("잘못된 메뉴를 입력하셨습니다."),
    VOUCHER_MENU_ERROR("잘못된 바우처를 입력하셨습니다."),
    EXIT("프로그램을 종료합니다.\n"),
    SELECT_VOUCHER("""
                바우처를 선택하세요
                1. FixedAmountVoucher Create
                2. PercentDiscountVoucher Create
                """),
    DISCOUNT_AMOUNT("할인금액: "),
    DISCOUNT_RATE("할인율: "),
    DISCOUNT_ERROR("할인 적용을 잘못하셨습니다.");

    private String message;

    Message(String message) {
        this.message = message;
    }


    public String getMessage() {
        return message;
    }
}
