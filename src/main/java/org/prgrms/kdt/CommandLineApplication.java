package org.prgrms.kdt;

import org.prgrms.kdt.service.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandLineApplication {
    public static void main(String[] args) throws IOException {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        VoucherService voucherService = applicationContext.getBean(VoucherService.class);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String command;
        int input;

        System.out.println("=== Voucher Program ===\n");
        while (true) {
            System.out.println(
                    "Type exit to exit the program.\n" +
                    "Type create to create a new voucher.\n" +
                    "Type list to list all vouchers.");
            command = br.readLine();
            if (command.equals("create")) {
                System.out.println("1을 누르면 FixedAmountVoucher 생성, 2를 누르면 PercentDiscountVoucher 생성");
                input = Integer.parseInt(br.readLine());
                String message = input == 1 ? "FixedAmountVoucher 생성" : "PercentDiscountVoucher 생성";
                System.out.println(message);
                voucherService.create(input);
            } else if (command.equals("list")) {
                System.out.println("바우처 조회");
                voucherService.list().forEach(System.out::println);
            } else if (command.equals("exit")) {
                System.out.println("종료합니다.");
                break;
            } else {
                System.out.println("잘못 입력했습니다.");
            }
        }
    }
}
