package org.prgrms.kdt.command;


import java.text.MessageFormat;
import org.prgrms.kdt.model.VoucherDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class CommandLineApplication implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

    private final Input input;
    private final Output output;
    private final CommandOperator operator;

    public CommandLineApplication(Input input, Output output, CommandOperator operator) {
        this.input = input;
        this.output = output;
        this.operator = operator;
    }

    @Override
    public void run() {

        String inputStr;
        var isExit = false;
        while (!isExit) {
            help();
            inputStr = input.input();
            switch (CommandType.lookup(inputStr)) {
                case CREATE -> create();
                case LIST -> list();
                case EXIT -> {
                    exit();
                    isExit = true;
                }
                default -> {
                    logger.warn(MessageFormat.format("invalid user command input: {0}", inputStr));
                    output.printError(inputStr);
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
        output.printMessage(prompt);
    }

    private void exit() {
        output.printMessage("Bye!!");
    }

    private void create() {
        var msg = "Creating a new voucher. Choose a voucher name (fixed or percent): ";
        var type = input.input(msg, Pattern.compile("^[a-z]*$").asPredicate());

        msg = "How much discount?: ";
        var value = input.input(msg, Pattern.compile("^[0-9]*$").asPredicate());

        var createdVoucher = operator.create(new VoucherDTO(type, Long.parseLong(value)));
        logger.info(MessageFormat.format("user create voucher: {0}", createdVoucher));
    }

    private void list() {
        var vouchers = operator.getAll();
        if (!vouchers.isEmpty()) {
            output.printMessage("=== Voucher List ===");
            vouchers.values().forEach(System.out::println);
        } else {
            output.printMessage("No Voucher Data");
        }
    }

}
