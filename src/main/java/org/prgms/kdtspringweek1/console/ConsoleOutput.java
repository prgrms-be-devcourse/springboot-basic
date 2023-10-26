package org.prgms.kdtspringweek1.console;

import org.springframework.stereotype.Component;

// 콘솔 출력의 경우, 출력 방법이 변경될 가능성이 적다고 판단하여 인터페이스 없이 바로 구현
@Component
public class ConsoleOutput {

    public void printFunctionsToSelect() {
        System.out.println("""
                --------------------------------------------------
                === Voucher Program ===
                Type exit to exit the program.
                Type create to create a new voucher.
                Type list to list all vouchers.
                Type blackList to list all black customers"""
        );
    }

    public void printExitMessage() {
        System.out.println("Voucher Program을 종료합니다.");
    }

    public void printVouchersToSelect() {
        System.out.println("""
                --------------------------------------------------
                생성할 voucher 종류를 선택하여 정수로 입력해주세요.
                1. FixedAmountVoucher
                2. PercentDiscountVoucher"""
        );
    }

    public void printRequestMessageToDecideFixedAmount() {
        System.out.println("고정된 할인 금액을 입력해주세요.");
    }

    public void printRequestMessageToDecidePercentDiscount() {
        System.out.println("할인율을 입력해주세요.");
    }

    public void printSuccessToCreateVoucher() {
        System.out.println("[System] 바우처 생성이 성공적으로 이루어졌습니다.");
    }

    public void printSuccessToSearchVouchers() {
        System.out.println("""
                --------------------------------------------------
                [System] 바우처 검색이 완료되었습니다."""
        );
    }

    public void printSuccessToSearchBlackCustomers() {
        System.out.println("""
                --------------------------------------------------
                [System] 블랙 리스트 검색이 완료되었습니다."""
        );
    }
}
