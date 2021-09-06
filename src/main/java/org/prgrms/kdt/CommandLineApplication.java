package org.prgrms.kdt;

import org.prgrms.kdt.command.CommandCreate;
import org.prgrms.kdt.voucher.Voucher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CommandLineApplication {
    public static void main(final String[] args) throws IOException, InterruptedException {
        final var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        final Scanner scanner = new Scanner(System.in);

        final List<Optional<Voucher>> mylist = new ArrayList<>();

        boolean programRunning = true;

        do {
            System.out.println("=== Voucher Program ===");
            System.out.println("Type 'exit' for Exit.");
            System.out.println("Type 'create' to create a new voucher");
            System.out.println("Type 'list' to list all vouchers");

            final String commandInput = scanner.nextLine();

            switch (commandInput) {
                case "exit":
                    programRunning = false;
                    break;

                case "create":
                    CommandCreate.voucherCreateMessage();
                    mylist.add(CommandCreate.createVoucherType());
                    break;

                case "list":
                    for (final Optional<Voucher> voucher : mylist) {
                        System.out.println(voucher);
                    }
                    break;

                default:
                    System.out.println("=== Input type error ===");
                    System.out.println(MessageFormat.format("{0}은(는) 지원하지 않는 명령어입니다.", commandInput));
                    break;
            }
        } while (programRunning);
    }
}
