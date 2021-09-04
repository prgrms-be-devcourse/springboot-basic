package org.prgrms.kdt;

import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

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
            switch (command) {
                case "create":
                    System.out.println("1을 누르면 FixedAmountVoucher 생성, 2를 누르면 PercentDiscountVoucher 생성");
                    input = Integer.parseInt(br.readLine());
                    String message = input == 1 ? "FixedAmountVoucher 생성" : "PercentDiscountVoucher 생성";
                    System.out.println(message);
                    Voucher voucher = input == 1 ? new FixedAmountVoucher(UUID.randomUUID(), 1L) : new PercentDiscountVoucher(UUID.randomUUID(), 1L);
                    voucherService.create(voucher);
                    break;
                case "list":
                    System.out.println("바우처 조회");
                    voucherService.list().forEach(System.out::println);
                    break;
                case "exit":
                    System.out.println("종료합니다.");
                    break;
                default:
                    System.out.println("잘못 입력했습니다.");
                    break;
            }
            if (command.equals("exit")) break;
        }
        applicationContext.close();
    }
}
