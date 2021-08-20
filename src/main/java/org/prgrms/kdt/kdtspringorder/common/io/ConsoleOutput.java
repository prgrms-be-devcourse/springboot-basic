package org.prgrms.kdt.kdtspringorder.common.io;

public class ConsoleOutput implements Output{

    private final String COMMAND_ANNOUNCE_MSG = "\n=== Voucher Program ===\nType exit to exit the program.\nType create to create a new voucher.\nType list to list all vouchers.\n";
    private final String REQUEST_INP_COMMAND_MSG = "명령어를 입력해 주세요. : ";
    private final String REQUEST_SELECT_VOUCHER_TYPE_MSG = "Voucher 유형을 골라주세요.(1) FixedAmountVoucher (2) PercentDiscountVoucher";
    private final String REQUEST_INPUT_DISCOUNT_MSG = "할인 {0}를 입력해주세요.";
    private final String INCORRECT_COMMAND_MSG = "잘못된 명령어 입니다. ( create, list, exit )";
    private final String INCORRECT_NUM_MSG = "잘못된 번호 입니다.";
    private final String EXIT_COMMAND_MSG = "앱을 종료합니다.";

    @Override
    public void showFirstMsg() {
        System.out.println(COMMAND_ANNOUNCE_MSG);
    }

    @Override
    public void showIncorrectCmdMsg() {
        System.out.println(INCORRECT_COMMAND_MSG);
    }

    @Override
    public void showExitMsg() {
        System.out.println(EXIT_COMMAND_MSG);
    }

    @Override
    public void showInccorectMsg() {
        System.out.println(INCORRECT_NUM_MSG);
    }

}
