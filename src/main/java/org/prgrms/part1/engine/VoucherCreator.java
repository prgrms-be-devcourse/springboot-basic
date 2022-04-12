package org.prgrms.part1.engine;

import org.prgrms.part1.exception.VoucherException;
import org.prgrms.part1.io.Input;
import org.prgrms.part1.io.Output;
import org.slf4j.Logger;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.UUID;
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
            var num = selectVoucherType();
            Optional<VoucherType> voucherType = matchVoucerType(num);
            if (voucherType.isEmpty()) {
                logger.debug("User select back to main");
                return;
            }
            var inputValue = input.inputQuestion("Type discount amount(percent) of Voucher : ");
            long value = parseValue(inputValue);
            Voucher voucher = voucherService.createVoucher(voucherType.get().createVoucher(UUID.randomUUID(), value));
            logger.info(MessageFormat.format("Create Voucher.\nVoucher Id: {0}\nVoucher Type: {1}\nVoucher Discount: {2}", voucher.getVoucherId(), voucher.getVoucherType()), voucher.getValue());
        }
    }

    private String selectVoucherType() {
        output.print("Select type of Voucher");
        output.print("1. Fixed Amount Voucher");
        output.print("2. Percent Amount Voucher");
        output.print("Other. Back to Main");
        return input.select();
    }

    private long parseValue(String inputValue) {
        if (Pattern.matches("[\\d]+", inputValue)) {
            return Long.parseLong(inputValue);
        } else {
            throw new VoucherException("Please type invalid number.");
        }
    }

    private Optional<VoucherType> matchVoucerType(String num) {
        return VoucherType.findMatchingCode(num);
    }
}
