package com.prgms.management.command;

import com.prgms.management.command.exception.CommandLineException;
import com.prgms.management.voucher.entity.FixedAmountVoucher;
import com.prgms.management.voucher.entity.PercentDiscountVoucher;
import com.prgms.management.voucher.entity.Voucher;
import com.prgms.management.voucher.service.VoucherService;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.stereotype.Component;

@Component
public class CommandLineApplication implements Runnable {
    private final TextIO textIO = TextIoFactory.getTextIO();
    private final VoucherService voucherService;

    public CommandLineApplication(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public void run() {
        boolean flag = true;
        while (flag) {
            try {
                CommandType command = getCommand();
                switch (command) {
                    case EXIT:
                        flag = false;
                        textIO.dispose();
                        break;
                    case LIST:
                        for (Voucher v : voucherService.getAllVouchers()) {
                            textIO.getTextTerminal().println(v.toString());
                        }
                        break;
                    case CREATE:
                        Voucher voucher = voucherService.saveVoucher(getVoucher());
                        textIO.getTextTerminal().println("Creation Success : " + voucher.toString());
                        break;
                }
            } catch (Exception e) {
                textIO.getTextTerminal().println(e.getMessage());
            }
            textIO.getTextTerminal().println();
        }
    }

    private CommandType getCommand() throws CommandLineException {
        textIO.getTextTerminal().println("=== Voucher Program ===");
        textIO.getTextTerminal().println("Type **exit** to exit the program.");
        textIO.getTextTerminal().println("Type **create** to create a new voucher.");
        textIO.getTextTerminal().println("Type **list** to list all vouchers.");
        textIO.getTextTerminal().println();

        String command = textIO.newStringInputReader()
                .withDefaultValue("list")
                .read("Command");

        switch (command) {
            case "list":
                return CommandType.LIST;
            case "create":
                return CommandType.CREATE;
            case "exit":
                return CommandType.EXIT;
            default:
                throw new CommandLineException();
        }
    }

    private Voucher getVoucher() throws CommandLineException {
        textIO.getTextTerminal().println("=== Create Voucher ===");
        textIO.getTextTerminal().println("Type **percent** to create a percent discount voucher.");
        textIO.getTextTerminal().println("Type **fixed** to create a fixed amount voucher.");
        textIO.getTextTerminal().println();

        String command = textIO.newStringInputReader()
                .withDefaultValue("fixed")
                .read("Voucher type");

        if (command.equals("fixed")) {
            Long amount = textIO.newLongInputReader()
                    .withMinVal(0L)
                    .read("Amount");
            return new FixedAmountVoucher(amount);
        } else if (command.equals("percent")) {
            Long percent = textIO.newLongInputReader()
                    .withMinVal(0L)
                    .read("Percent");
            return new PercentDiscountVoucher(percent);
        } else {
            throw new CommandLineException();
        }
    }

    private enum CommandType {
        CREATE, LIST, EXIT
    }
}
