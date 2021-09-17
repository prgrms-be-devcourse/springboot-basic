package org.prgrms.dev.command;

import org.prgrms.dev.io.Input;
import org.prgrms.dev.io.Output;
import org.prgrms.dev.voucher.exception.InvalidArgumentException;
import org.prgrms.dev.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class CreateCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(CreateCommand.class);
    private static final String CURSOR = "> ";

    @Override
    public boolean execute(Input input, Output output, VoucherService voucherService) {
        try {
            output.selectVoucherType();
            String voucherType = input.input(CURSOR);
            long value = Long.parseLong((input.input("input value [fixed amount | percent discount] " + CURSOR)));
            voucherService.createVoucher(voucherType, UUID.randomUUID(), value);
        } catch (NumberFormatException | InvalidArgumentException e) {
            logger.error(e.getMessage());
            output.printInvalidNumber();
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            output.printInvalidVoucherType();
        }
        return true;
    }
}

