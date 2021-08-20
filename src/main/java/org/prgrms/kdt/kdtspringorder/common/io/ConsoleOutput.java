package org.prgrms.kdt.kdtspringorder.common.io;

/**
 * 콘솔 출력을 담당합니다.
 */
public class ConsoleOutput implements Output{

    private final String COMMAND_ANNOUNCE_MSG = "\n=== Voucher Program ===\nType exit to exit the program.\nType create to create a new voucher.\nType list to list all vouchers.\n";
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
    public void showIncorrectNumMsg() {
        System.out.println(INCORRECT_NUM_MSG);
    }

}
