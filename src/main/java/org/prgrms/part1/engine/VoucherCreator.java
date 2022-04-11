package org.prgrms.part1.engine;

import org.prgrms.part1.exception.InputException;
import org.prgrms.part1.io.Input;
import org.prgrms.part1.io.Output;
import org.slf4j.Logger;

import java.text.MessageFormat;
import java.util.regex.Pattern;

public class VoucherCreator implements Runnable {
    private final VoucherService voucherService;
    private final Input input;
    private final Output output;
    private final Logger logger;

    public VoucherCreator(VoucherService voucherService, Input input, Output output, Logger logger) {
        this.voucherService = voucherService;
        this.input = input;
        this.output = output;
        this.logger = logger;
    }

    @Override
    public void run() {
        while (true) {
            VoucherType type;
            output.print("Select type of Voucher");
            output.print("1. Fixed Amount Voucher");
            output.print("2. Percent Amount Voucher");
            output.print("3. Back to Main");
            var num = input.select();

            switch (num) {
                case "1" -> {
                    logger.debug("User select fixed amount Voucher");
                    type = VoucherType.FixedAmount;
                }
                case "2" -> {
                    logger.debug("User select percent discount voucher");
                    type = VoucherType.PercentDiscount;
                }
                case "3" -> {
                    logger.debug("User select back to main");
                    return;
                }
                default -> {
                    logger.warn("User select invalid voucher");
                    output.printError("Please Type Valid Number");
                    continue;
                }
            }
            var inputValue = input.inputQuestion("Type discount amount(percent) of Voucher : ");
            int value = parseValue(inputValue);
            Voucher voucher = voucherService.createVoucher(type, value);
            logger.info(MessageFormat.format("Create Voucher.\nVoucher Id: {0}\nVoucher Type: {1}\nVoucher Discount: {2}", voucher.getVoucherId(), voucher.getVoucherType()), voucher.getValue());
        }
    }

    private int parseValue(String inputValue) {
        if (Pattern.matches("[\\d]+", inputValue)) {
            return Integer.parseInt(inputValue);
        } else {
            throw new InputException("Please type invalid number.");
        }
    }
}
