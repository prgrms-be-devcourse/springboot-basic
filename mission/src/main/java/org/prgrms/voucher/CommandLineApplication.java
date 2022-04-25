package org.prgrms.voucher;

import org.prgrms.voucher.io.Input;
import org.prgrms.voucher.io.Output;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CommandLineApplication implements ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

    private final Input input;
    private final Output output;
    private final Converter converter;

    public CommandLineApplication(Input input, Output output, Converter converter) {

        this.input = input;
        this.output = output;
        this.converter = converter;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        while (true) {
            CommandType commandType;
            output.printPrompt();
            String userInput = input.getString();

            try {
                commandType = CommandType.findByCommand(userInput);
            } catch (IllegalArgumentException inputException) {
                logger.error("{} Type menu error", inputException.getMessage(), inputException);
                output.printInvalidInputError();
                continue;
            }

            switch (commandType) {
                case CREATE -> creatVoucher();
                case LIST -> showVoucherList();
                case EXIT -> System.exit(0);
            }
        }
    }

    private void creatVoucher() {

        String response;

        output.printVoucherType();
        String typeSelect = input.getString();
        output.printVoucherDiscountType();
        String discountValue = input.getString();
        response = converter.createVoucherRequest(typeSelect, discountValue);
        output.printMessage(response);
    }

    private void showVoucherList() {

        String response;

        response = converter.showVoucherListRequest();
        output.printMessage(response);
    }
}
