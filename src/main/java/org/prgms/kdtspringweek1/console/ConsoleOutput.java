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
                                
                Type "create voucher" to create a new voucher.
                Type "list vouchers" to list all vouchers.
                Type "search voucher" to find voucher by id.
                Type "update voucher" to update voucher info.
                Type "delete all vouchers" to delete voucher by id.
                Type "delete voucher" to delete all vouchers.
                                
                Type "create customer" to create a new customer.
                Type "list customers" to list all customers.
                Type "search customer" to find customer by id.
                Type "update customer" to update customer info.
                Type "delete all customers" to delete customer by id.
                Type "delete customer" to delete all customers.
                Type "list black customers" to list all black customers.
                                
                Type "create my wallet" to create a wallet.
                Type "search my vouchers" to list vouchers owned by a customer.
                Type "delete my wallet" to delete a wallet.
                Type "search my customers" to list customers owned by a voucher."""
        );
    }

    public void printExitMessage() {
        System.out.println("Voucher Program을 종료합니다.");
    }

    public void printVouchersToSelect() {
        System.out.println("""
                --------------------------------------------------
                voucher 종류를 선택하여 문자열로 입력해주세요.
                1. fixed amount voucher
                2. percent discount voucher"""
        );
    }

    public void printRequestMessageforDiscountValue() {
        System.out.println("할인 금액/할인율을 입력해주세요.");
    }

    public void printRequestMessageForVoucherId() {
        System.out.println("voucher id를 입력해주세요.");
    }

    public void printRequestMessageForCustomerId() {
        System.out.println("customer id를 입력해주세요.");
    }

    public void printRequestMessageForName() {
        System.out.println("이름를 입력해주세요.");
    }

    public void printRequestMessageForIsBlackCustomer() {
        System.out.println("블랙 컨슈머 여부를 true/false로 입력해주세요.");
    }

    public void printSuccessToCreate() {
        System.out.println("[System] 생성이 성공적으로 이루어졌습니다.");
    }

    public void printSuccessToSearch() {
        System.out.println("""
                --------------------------------------------------
                [System] 검색이 완료되었습니다."""
        );
    }

    public void printSuccessToUpdate() {
        System.out.println("[System] 수정이 성공적으로 이루어졌습니다.");
    }

    public void printSuccessToDelete() {
        System.out.println("[System] 삭제가 성공적으로 이루어졌습니다.");
    }

    public void printValueNotFound() {
        System.out.println("[System] id에 해당하는 값이 존재하지 않습니다.");
    }
}
