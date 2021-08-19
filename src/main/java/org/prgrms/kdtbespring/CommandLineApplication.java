package org.prgrms.kdtbespring;

import org.prgrms.kdtbespring.config.AppConfiguration;
import org.prgrms.kdtbespring.entity.Voucher;
import org.prgrms.kdtbespring.service.VoucherService;
import org.prgrms.kdtbespring.vo.VoucherType;
import org.springframework.context.ApplicationContext;
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
        sb.append("Type \"exit\" to exit the program." + "\n");
        sb.append("Type \"create\" to create a new voucher." + "\n");
        sb.append("Type \"list\" to list all voucher.");

        ApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        VoucherService voucherService = annotationConfigApplicationContext.getBean(VoucherService.class);

        while (true) {
            System.out.println(sb);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String Command = br.readLine();
            Command = Command.toLowerCase();
            if (Command.equals("exit")) {
                System.out.println("Program Exit");
                break;
            } else if (Command.equals("create")) {
                System.out.println("생성할 바우처를 선택하세요.");
                System.out.println("1. FixedAmountVoucher, 2. PercentDiscountVoucher");
                String select = br.readLine();

                if (select.equals("1")) {
                    Optional<Voucher> voucher = voucherService.create(VoucherType.FixedAmountVoucher);
                    if (voucher.isPresent()) {
                        System.out.println(voucher.get());
                    } else {
                        System.out.println("생성 실패");
                    }
                } else if (select.equals("2")) {
                    Optional<Voucher> voucher = voucherService.create(VoucherType.PercentDiscountVoucher);
                    if (voucher.isPresent()) {
                        System.out.println(voucher.get());
                    } else {
                        System.out.println("생성 실패");
                    }
                } else {
                    System.out.println("입력 실패(번호를 입력해주세요.)");
                }

            } else if (Command.equals("list")) {
                List<Voucher> list = voucherService.list();
                for (int i = 0; i < list.size(); i++) {
                    Voucher voucher = list.get(i);
                    System.out.println(i + 1 + "번 바우처 : " + voucher);
                }
            } else {
                System.out.println("exit, list, create 중에서 입력히세요");
            }
        }

    }
}
