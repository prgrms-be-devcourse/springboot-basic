package org.prgrms.voucher;

import org.prgrms.voucher.controller.VoucherController;
import org.prgrms.voucher.dto.VoucherDto;
import org.prgrms.voucher.io.Input;
import org.prgrms.voucher.io.Output;
import org.prgrms.voucher.models.VoucherType;
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
    private final VoucherController voucherController;

    public CommandLineApplication(Input input, Output output, VoucherController voucherController) {

        this.input = input;
        this.output = output;
        this.voucherController = voucherController;
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
                case CREATE -> {
                    try {
                        output.printVoucherType();
                        VoucherType voucherType = input.getVoucherType();
                        output.printVoucherDiscountType();
                        long discountValue = input.getDiscountValue();
                        voucherController.create(new VoucherDto.VoucherRequest(discountValue, voucherType));
                    } catch (IllegalArgumentException illegalArgumentException) {
                        output.printInvalidInputError();
                    }
                }
                case LIST -> output.printVoucherList(voucherController.list().data());

                case EXIT -> System.exit(0);
            }
        }
    }
}
