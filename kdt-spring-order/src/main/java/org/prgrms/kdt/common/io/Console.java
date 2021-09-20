package org.prgrms.kdt.common.io;

import org.prgrms.kdt.customer.model.Customer;
import org.prgrms.kdt.voucher.model.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public class Console implements Input, Output{
    Scanner scanner = new Scanner(System.in);
    // 로거 생성
    private static final Logger logger = LoggerFactory.getLogger(Console.class);

    // 시스템 시작시 안내 문구
    @Override
    public void printInitText() {
        logger.info("Voucher Application 실행");
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
    }

    @Override
    public void printExitText() {
        logger.info("Voucher Application 종료");
    }

    // 커맨드 오류시 에러출력
    @Override
    public void printCommandError(String command) {
        logger.error("올바르지 않은 명령어 입력 : {}", command);
    }

    @Override
    public void printSuccess() {
        logger.info("Voucher 생성 완료");
    }

    @Override
    public void printVoucherList(List<Voucher> voucherList) {
        logger.info("Voucher 리스트 조회");
        System.out.println("-----------------------------------------------------------------------");
        for(Voucher voucher : voucherList){
            System.out.println(
                    "ID : " + voucher.getVoucherId() + " | " +
                    "value : " + voucher.getValue() + " | " +
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

    @Override
    public String inputCommand() {
        System.out.print("명령어를 입력하세요(create/list/exit) : ");
        return scanner.nextLine();
    }

    @Override
    public String inputVoucherType() {
        System.out.print("바우처 종류를 고르세요(fixed/percent) : ");
        return scanner.nextLine();
    }

    @Override
    public Long inputVoucherValue() {
        System.out.print("할인가격 or 할인율을 입력하세요 : ");
        return Long.parseLong(scanner.nextLine());
    }
}
