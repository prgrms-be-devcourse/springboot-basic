package org.prgrms.kdt.helper;

public class MessageHelper {

    public void showVoucherProgramGuideMessage() {
        System.out.println("=== Voucher Program ===");
        System.out.println("원하는 기능을 입력하세요.");
        System.out.println("exit");
        System.out.println("create");
        System.out.println("list");
        System.out.println("replay");
        System.out.println("=======================");
    }

    public void showExitMessage() {
        System.out.println("프로그램을 종료하겠습니다.");
    }

    public void showRetryMessage() {
        System.out.println("잘못입력하셨습니다. 다시 선택해주세요.");
    }

    public void showVoucherSelectionMessage() {
        System.out.println("Voucher 종류를 입력해주세요.");
        System.out.println("fixed");
        System.out.println("discount");
    }

    public void showVoucherRegistrationSuccessMessage() {
        System.out.println("Voucher 등록이 완료되었습니다.");
    }

    public void showEnterVoucherDiscount() {
        System.out.println("할인율을 입력해주세요.");
    }

    public void showDuplicateVoucherMessage() {
        System.out.println("중복된 바우처 입니다.");
    }

}