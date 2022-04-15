package org.prgrms.kdt.io;

public class OutputConsole implements Output {

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void printCommandManual() {
        printMessage(
            "=== Voucher Program ===\n" +
            "Type exit to exit the program.\n" +
            "Type create to create a new voucher.\n" +
            "Type list to list all vouchers."
        );
    }

    @Override
    public void printInvalidCommand() {
        printMessage("유효하지 않은 명령입니다.");
    }

    @Override
    public void printShutDownSystem() {
        printMessage("시스템이 종료되었습니다.");
    }

}
