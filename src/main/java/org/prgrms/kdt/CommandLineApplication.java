package org.prgrms.kdt;

import org.prgrms.kdt.command.CommandCreate;
import org.prgrms.kdt.command.NavigationMessage;
import org.prgrms.kdt.voucher.repository.MemoryVoucherRepository;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.Scanner;

public class CommandLineApplication {
    public static void main(final String[] args) throws IOException, InterruptedException {
        final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        final VoucherRepository voucherRepository = applicationContext.getBean(MemoryVoucherRepository.class);

        final Scanner scanner = new Scanner(System.in);
        boolean programRunning = true;

        do {
            new NavigationMessage();

            final String commandInput = scanner.nextLine();
            switch (commandInput) {
                case "exit":
                    programRunning = false;
                    break;

                case "create":
                    NavigationMessage.voucherCreateMessage();
                    voucherRepository.insert(
                            CommandCreate.createVoucherType()
                    );
                    break;

                case "list":
                    for (int i = 0; i < voucherRepository.findAll().size(); i++) {
                        System.out.println(voucherRepository.findAll().get(i));
                    }
                    break;

                default:
                    NavigationMessage.inputTypeErrorMessage(commandInput);
                    break;
            }
        } while (programRunning);
    }
}
