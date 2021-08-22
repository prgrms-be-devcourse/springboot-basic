package org.prgrms.kdt.io;

import org.prgrms.kdt.customer.domain.Customer;
import org.prgrms.kdt.voucher.domain.Voucher;

import java.util.List;
import java.util.Scanner;

public class Console implements Input, Output{
    Scanner scanner = new Scanner(System.in);

    // 시스템 시작시 안내 문구
    @Override
    public void printInitText() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
    }

    @Override
    public void printExitText() {
        System.out.println("프로그램을 종료합니다.");
    }

    // 커맨드 오류시 에러출력
    @Override
    public void printCommandError(String command) {
        System.out.println(command + "는 올바르지 않는 명령어입니다.");
    }

    @Override
    public void printSuccess() {
        System.out.println("Voucher 생성이 완료되었습니다!");
    }

    @Override
    public void printVoucherList(List<Voucher> voucherList) {
        System.out.println("-----------------------------------------------------------------------");
        for(Voucher voucher : voucherList){
            System.out.println(
                    "ID : " + voucher.getVoucherId() + " | " +
                    "value : " + voucher.getVoucherValue() + " | " +
                    "type : " + voucher.getVoucherType()
            );
        }
        System.out.println("-----------------------------------------------------------------------");
    }

    @Override
    public void printBlackList(List<Customer> customerList) {
        System.out.println("---------------------------- 블랙리스트 명단 ----------------------------");
        for(Customer customer : customerList){
            System.out.println(customer.getName());
        }
        System.out.println("-----------------------------------------------------------------------");
    }

    // 커맨드 입력받기
    @Override
    public String inputCommand(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }



}
