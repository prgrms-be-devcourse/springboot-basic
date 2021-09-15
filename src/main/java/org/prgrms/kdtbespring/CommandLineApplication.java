package org.prgrms.kdtbespring;

import org.prgrms.kdtbespring.command.Command;
import org.prgrms.kdtbespring.config.AppConfiguration;
import org.prgrms.kdtbespring.voucher.Voucher;
import org.prgrms.kdtbespring.voucher.VoucherService;
import org.prgrms.kdtbespring.voucher.VoucherType;
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

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean status = true;

        while (status) {
            System.out.println(sb);

            String input = br.readLine();
            input = input.toUpperCase();

            try {
                Command command = Command.valueOf(input);
                status = command.run(voucherService);
            } catch (IllegalArgumentException e) {
                System.out.println("exit, list, create 중에서 입력히세요");
            }
        }
    }
}
