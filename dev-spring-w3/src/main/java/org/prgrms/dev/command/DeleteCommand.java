package org.prgrms.dev.command;

import org.prgrms.dev.exception.NotFoundException;
import org.prgrms.dev.io.Input;
import org.prgrms.dev.io.Output;
import org.prgrms.dev.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class DeleteCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(DeleteCommand.class);
    private static final String CURSOR = "> ";

    @Override
    public boolean execute(Input input, Output output, VoucherService voucherService) {
        try {
            String voucherId = (input.input("삭제를 원하는 바우처 아이디를 입력하세요. " + CURSOR));
            voucherService.deleteVoucher(UUID.fromString(voucherId));
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
            output.printNotFound(e.getMessage());
        }
        return true;
    }
}
