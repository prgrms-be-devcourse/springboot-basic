package org.prgrms.dev.command;

import org.prgrms.dev.exception.InvalidArgumentException;
import org.prgrms.dev.exception.NotFoundException;
import org.prgrms.dev.exception.NotUpdateException;
import org.prgrms.dev.io.Input;
import org.prgrms.dev.io.Output;
import org.prgrms.dev.voucher.domain.dto.UpdateVoucherDto;
import org.prgrms.dev.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.UUID;

public class UpdateCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(UpdateCommand.class);
    private static final String CURSOR = "> ";

    @Override
    public boolean execute(Input input, Output output, VoucherService voucherService) {
        try {
            String voucherId = (input.input("변경을 원하는 바우처 아이디를 입력하세요. " + CURSOR));
            long discount = Long.parseLong((input.input("변경할 할인정보를 입력하세요. " + CURSOR)));
            UpdateVoucherDto voucherDto = new UpdateVoucherDto(UUID.fromString(voucherId), discount);
            voucherService.modifyVoucher(voucherDto);
        } catch (NumberFormatException | InvalidArgumentException e) {
            logger.error(e.getMessage());
            output.printInvalidNumber(e.getMessage());
        } catch (NotFoundException e) {
            logger.error(e.getMessage());
            output.printNotFound(e.getMessage());
        } catch (NotUpdateException e) {
            logger.error(e.getMessage());
        }
        return true;
    }
}
