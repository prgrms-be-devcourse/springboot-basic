package org.prgrms.kdt;

import org.prgrms.kdt.command.CommandCreate;
import org.prgrms.kdt.command.NavigationMessage;
import org.prgrms.kdt.voucher.Voucher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CommandLineApplication implements Runnable {
    public static void main(final String[] args) throws IOException, InterruptedException {
        final Scanner scanner = new Scanner(System.in);

        final List<Optional<Voucher>> voucherList = new ArrayList<>();
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
                    voucherList.add(CommandCreate.createVoucherType());
                    break;

                case "list":
                    voucherList.forEach(System.out::println);
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
