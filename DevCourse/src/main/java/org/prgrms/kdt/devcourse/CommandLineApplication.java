package org.prgrms.kdt.devcourse;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class CommandLineApplication {


    public static void main(String[] args) {

        final String CMD_CREATE = "create";
        final String CMD_LIST = "list";
        final String CMD_EXIT = "exit";

        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var voucherService = applicationContext.getBean(VoucherService.class);



        var info = """
                === Voucher Program ===
                Type exit to exit the program.
                Type create to create a new voucher.
                Type list to list all vouchers.
                """;

        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println(info);
            String cmd = scanner.nextLine();
            switch (cmd) {
                case CMD_CREATE -> {
                    System.out.println("고정 값 바우처 발행은 1, 퍼센트 할인 바우처 발행은 2 입력");
                    String voucherInput = scanner.nextLine();
                    if (voucherInput.equals("1")) {
                        voucherService.createVoucher(VoucherType.FIXED_AMOUNT, 10L);
                        System.out.println("고정 값 할인 바우처 생성!");
                    } else if (voucherInput.equals("2")) {
                        voucherService.createVoucher(VoucherType.PERCENTAGE, 10L);
                        System.out.println("퍼센트 할인 바우처 생성!");
                    }
                }
                case CMD_LIST -> {
                    var voucherList = voucherService.getAllVouchers();
                    if (voucherList.isEmpty()) {
                        System.out.println("등록된 바우처가 없습니다.");
                    } else {
                        for (Voucher voucher : voucherList) {
                            System.out.println("바우처 " + voucher.getVoucherId());
                        }
                    }
                }
                case CMD_EXIT -> {
                    System.out.println("프로그램을 종료합니다.");
                    System.exit(0);
                }
                default -> System.out.println("없는 명령어입니다.");
            }
        }
    }



}
