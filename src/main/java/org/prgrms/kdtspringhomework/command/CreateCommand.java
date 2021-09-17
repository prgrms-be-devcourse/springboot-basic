package org.prgrms.kdtspringhomework.command;

import org.prgrms.kdtspringhomework.command.create.CreateVoucherStatus;
import org.prgrms.kdtspringhomework.io.Input;
import org.prgrms.kdtspringhomework.io.Output;
import org.prgrms.kdtspringhomework.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateCommand implements CommandStrategy {
    private static final Logger logger = LoggerFactory.getLogger(CreateCommand.class);

    @Override
    public boolean execute(Input input, Output output, VoucherService voucherService) {
        boolean flag = true;
        try {
            output.inputVoucherTypeMessage();
            String voucherType = input.receiveUserInput();
            output.inputAmountMessage();
            long amount = 0L;
            try {
                amount = Long.parseLong(input.receiveUserInput());
            } catch (NumberFormatException e) {
                logger.error(e.getMessage());
                output.invalidAmount();
            }
            flag = CreateVoucherStatus.create(voucherService, voucherType, amount);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            output.invalidVoucherType();
        }
        return flag;
    }
}
