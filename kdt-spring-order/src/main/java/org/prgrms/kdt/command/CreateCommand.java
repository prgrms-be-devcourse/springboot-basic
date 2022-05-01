package org.prgrms.kdt.command;

import org.prgrms.kdt.blacklist.service.BlacklistService;
import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(CreateCommand.class);

    @Override
    public boolean execute(Input input, Output output, VoucherService voucherService, BlacklistService blacklistService) {

        while(true) {
            try { // Get voucher type for new voucher.
                output.selectVoucherType();
                String voucherType = input.input("> ");
                VoucherType.checkVoucherType(voucherType);

                // Get discount rate for new voucher.
                output.setVoucherDiscount();
                int discountRate = Integer.parseInt(input.input("> "));

                // Create and save new voucher.
                voucherService.createVoucher(voucherType, discountRate);
                break;
            } catch (IllegalArgumentException e) {
                logger.error(e.getMessage());
                output.printInvalidCmd(e.getMessage());
            }
        }

        return true;
    }
}
