package org.prgrms.kdtspringorder;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

public class CommandLineApplication {
    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Voucher Program ===" + "\n");
        sb.append("Type exit to exit the program." + "\n");
        sb.append("Type create to create a new voucher." + "\n");
        sb.append("Type list to list all voucher.");

        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var voucherService = applicationContext.getBean(VoucherService.class);
        System.out.println(sb);
        while (true) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String Com = br.readLine();
            if (Com.equals("exit")) {
                System.out.println("Program Exit");
                break;
            } else if (Com.equals("create")) {
                System.out.println("선택하세요 :");
                System.out.println("1. FixedAmountVoucher, 2. PercentDiscountVoucher");
                String select = br.readLine();
                if (select.equals("1")) {
                    Optional<Voucher> voucher = voucherService.createVoucher(VoucherType.FixedAmountVoucher);
                    voucherService.addVoucher(voucher);
                    System.out.println("생성이 완료 되었습니다.");
                } else if (select.equals("2")) {
                    Optional<Voucher> voucher = voucherService.createVoucher(VoucherType.PercentDiscountVoucher);
                    voucherService.addVoucher(voucher);
                    System.out.println("생성이 완료 되었습니다.");
                } else {
                    System.out.println("다시입력하세요");
                }
            } else if (Com.equals("list")) {
                    List<Voucher> list = voucherService.getAllVouchers();
                    for (int i = 0; i < list.size(); i++) {
                        Voucher voucher = list.get(i);
                        System.out.println(i +1 + "번 바우처 : " + voucher);
                    }
                } else {
                    System.out.println("exit, list, create 중에서 입력히세요");
                }
            }

        }
    }
