package org.prgrms.kdt.command;


import java.text.MessageFormat;
import org.prgrms.kdt.model.Voucher;
import org.prgrms.kdt.model.VoucherDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class CommandLineApplication implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

    private final Console console;
    private final CommandOperator<Voucher, VoucherDTO> operator;

    public CommandLineApplication(Console console, CommandOperator<Voucher, VoucherDTO> operator) {
        this.console = console;
        this.operator = operator;
    }

    @Override
    public void run() {

        String inputStr;
        var isExit = false;
        while (!isExit) {
            help();
            inputStr = console.input();
            switch (CommandType.lookup(inputStr)) {
                case CREATE -> create();
                case LIST -> list();
                case EXIT -> {
                    exit();
                    isExit = true;
                }
                default -> {
                    logger.warn(MessageFormat.format(
                        "invalid user command input: {0}", inputStr));
                    console.printError(inputStr);
                }
            }
        }
    }

    private void help() {
        var prompt = """
            === Voucher Program ===
            Type exit to exit the program.
            Type create to create a new voucher.
            Type list to list all vouchers.""";
        console.printMessage(prompt);
    }

    private void create() {
        var msg = "Creating a new voucher. Choose a voucher name (fixed or percent): ";
        var type = console.input(msg, Pattern.compile("^[a-z]*$").asPredicate());

        msg = "How much discount?: ";
        var value = console.input(msg, Pattern.compile("^[0-9]*$").asPredicate());

        var createdVoucher = operator.create(new VoucherDTO(type, Long.parseLong(value)));
        logger.info(MessageFormat.format("user create voucher: {0}", createdVoucher));
    }

    private void list() {
        var vouchers = operator.getAll();
        if (!vouchers.isEmpty()) {
            console.printMessage("=== Voucher List ===");
            vouchers.values().forEach(System.out::println);
        } else {
            console.printMessage("No Voucher Data");
        }
    }

    private void exit() {
        console.printMessage("Bye!!");
    }

}
