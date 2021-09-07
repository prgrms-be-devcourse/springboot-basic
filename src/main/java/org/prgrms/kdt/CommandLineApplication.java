package org.prgrms.kdt;

import org.prgrms.kdt.command.CommandCreate;
import org.prgrms.kdt.command.NavigationMessage;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.repository.MemoryVoucherRepository;
import org.prgrms.kdt.voucher.repository.VoucherRepository;

import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

public class CommandLineApplication implements Runnable {
    public static void main(final String[] args) throws IOException, InterruptedException {
        final VoucherRepository voucherRepository = new MemoryVoucherRepository();
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
                    voucherRepository.addVoucher(
                            CommandCreate.createVoucherType()
                    );
                    break;

                case "list":
                    for (final Optional<Voucher> voucher : voucherRepository.findAll()) {
                        System.out.println(voucher.get());
                    }
                    break;

                default:
                    NavigationMessage.inputTypeErrorMessage(commandInput);
                    break;
            }
        } while (programRunning);
    }

    @Override
    public void run() {

    }
}
