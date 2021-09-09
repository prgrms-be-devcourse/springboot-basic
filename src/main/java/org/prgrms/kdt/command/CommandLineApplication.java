package org.prgrms.kdt.command;

import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CommandLineApplication {
    public void run(final VoucherRepository voucherRepository) {
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
