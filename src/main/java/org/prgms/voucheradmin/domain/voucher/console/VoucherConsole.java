package org.prgms.voucheradmin.domain.voucher.console;

import static org.prgms.voucheradmin.domain.voucher.console.Command.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

import org.prgms.voucheradmin.domain.voucher.exception.WrongInputException;
import org.prgms.voucheradmin.domain.voucher.service.VoucherService;

public class VoucherConsole {
    private final VoucherService voucherService;

    public VoucherConsole(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public void run() {
        boolean keepRun = true;

        while (keepRun) {
            try {
                showCommandList();
                Command command = selectCommand();

                switch (command) {
                    case CREATE:
                        System.out.println(CREATE);
                        break;
                    case LIST:
                        System.out.println(LIST);
                        break;
                    default:
                        keepRun = false;
                        break;
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void showCommandList() {
        StringBuilder commandListBuilder = new StringBuilder();

        commandListBuilder.append("\n=== Voucher Program ===\n")
                .append("Type exit to exit the program.\n")
                .append("Type create to create a new voucher.\n")
                .append("Type list to list all vouchers.\n");

        System.out.println(commandListBuilder.toString());
    }

    private Command selectCommand() throws IOException, WrongInputException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String selectedCommand = br.readLine().trim();

        Command command = findCommand(selectedCommand);

        return command;
    }
}
