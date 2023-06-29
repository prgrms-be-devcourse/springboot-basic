package com.demo.voucher.io.exception;

public interface InputError {
    String INPUT_COMMAND_ERROR = "올바른 명령어를 입력하지 않았습니다.";
    String INPUT_VOUCHER_TYPE_ERROR = "올바른 바우처 타입을 입력하지 않았습니다.";
    String INPUT_AMOUNT_ERROR = "올바른 할인 금액을 입력하지 않았습니다.";


    default void showCommandError() {
        System.out.println(INPUT_COMMAND_ERROR);
    }

    default void showVoucherTypeError() {
        System.out.println(INPUT_VOUCHER_TYPE_ERROR);
    }

    default void showAmountError() {
        System.out.println(INPUT_AMOUNT_ERROR);
    }
}
