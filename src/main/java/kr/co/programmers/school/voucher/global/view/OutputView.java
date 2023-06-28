package kr.co.programmers.school.voucher.global.view;

import kr.co.programmers.school.voucher.domain.voucher.dto.VoucherListResponse;

public class OutputView {
    public void printCommand() {
        System.out.println("=== Voucher Program ===");
        System.out.println("1. 바우처 생성\n2. 바우처 조회\n3. 종료");
        printInputMessage();
    }

    public void printVoucherType() {
        System.out.println("생성할 바우처 타입을 입력하세요.\n1. 금액 할인\n2. 비율 할인");
        printInputMessage();
    }

    public void printPercentDiscountVoucherMessage() {
        System.out.println("할인 비율을 입력하세요. (1 ~ 100 사이 자연수만 가능)");
        printInputMessage();
    }

    public void printFixedDiscountVoucherMessage() {
        System.out.println("할인 금액을 입력하세요. (2,147,483,647 이하 자연수만 가능)");
        printInputMessage();
    }

    public void printSuccessMessage() {
        System.out.println("바우처를 성공적으로 생성하였습니다.");
    }

    public void printVoucherList(VoucherListResponse voucherListResponse) {
        if (voucherListResponse.isEmpty()) {
            System.out.println("등록된 바우처가 없습니다.");
            return;
        }

        System.out.println(voucherListResponse.getVoucherList());
    }

    private void printInputMessage() {
        System.out.print("입력 : ");
    }
}