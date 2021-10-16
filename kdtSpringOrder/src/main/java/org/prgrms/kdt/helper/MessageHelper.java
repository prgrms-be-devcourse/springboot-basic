package org.prgrms.kdt.helper;

public class MessageHelper {

    public static void showVoucherProgramGuideMessage() {
        System.out.println("=== Voucher Program ===");
        System.out.println("원하는 기능을 입력하세요.");
        System.out.println("exit");
        System.out.println("create");
        System.out.println("voucherlist");
        System.out.println("voucherlistbycustomerid");
        System.out.println("voucherbyvoucherid");
        System.out.println("deletevoucher");
        System.out.println("blacklist");
        System.out.println("replay");
        System.out.println("=======================");
    }

    public static void showExitMessage() {
        System.out.println("프로그램을 종료하겠습니다.");
    }

    public static void showRetryMessage() {
        System.out.println("잘못입력하셨습니다. 다시 선택해주세요.");
    }

    public static void showVoucherSelectionMessage() {
        System.out.println("Voucher 종류를 입력해주세요.");
        System.out.println("fixed");
        System.out.println("discount");
    }

    public static void showEnterCustomerIdMessage() { System.out.println("costomerId 를 입력해주세요."); }

    public static void showEnterVoucherIdMessgae() { System.out.println("voucherId 를 입력해주세요."); }

    public static void showVoucherRegistrationSuccessMessage() {
        System.out.println("Voucher 등록이 완료되었습니다.");
    }

    public static void showEnterVoucherDiscount() {
        System.out.println("할인율을 입력해주세요.");
    }

    public static void showEnterDeleteVoucherInfo() { System.out.println("고객의 아이디와 바우처 아이디를 입력해주세요."); }

    public static void showDuplicateVoucherMessage() {
        System.out.println("중복된 바우처 입니다.");
    }

    public static void showVoucherListEmptyMessage() { System.out.println("등록된 바우처가 없습니다."); };

    public static void showBadCustomerListEmptyMessage() { System.out.println("조회된 고객이 없습니다."); };

}