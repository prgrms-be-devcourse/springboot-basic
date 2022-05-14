package org.programmers.springbootbasic.command;

import javassist.NotFoundException;
import org.programmers.springbootbasic.exception.NotUpdateException;
import org.programmers.springbootbasic.io.Console;
import org.programmers.springbootbasic.voucher.controller.api.UpdateVoucherRequest;
import org.programmers.springbootbasic.voucher.service.DefaultVoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class UpdateCommand implements Command{
    private static final Logger logger = LoggerFactory.getLogger(UpdateCommand.class);

    @Override
    public boolean execute(Console console, DefaultVoucherService defaultVoucherService) {
        try {
            String voucherId = console.input("Type Change VoucherId");
            long value = Long.parseLong(console.input("Type Change value"));
            defaultVoucherService.updateVoucher(new UpdateVoucherRequest(UUID.fromString(voucherId), value));
            console.printSuccessUpdateMessage();
        } catch (NumberFormatException e) {
            logger.error("NumberFormat Exception 입니다", e);
        } catch (IllegalArgumentException e) {
            logger.error("잘못된 입력 입니다.", e);
        } catch (NotUpdateException e) {
            logger.error("업데이트에 실패하였습니다.", e);
        } catch (NotFoundException e) {
            logger.error("존재하지 않는 값 입니다.");
        }
        return true;
    }
}
