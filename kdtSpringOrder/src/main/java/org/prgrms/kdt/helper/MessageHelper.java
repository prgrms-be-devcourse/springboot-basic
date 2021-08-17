package org.prgrms.kdt.helper;

public class MessageHelper {

    public void showVoucherProgramGuideMessage() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher");
        System.out.println("Type list to list all vouchers");
    }

    public void showExitMessage() {
        System.out.println("프로그램을 종료하겠습니다.");
    }

    public void showRetryMessage() {
        System.out.println("잘못입력하셨습니다. 다시 선택해주세요.");
    }

}