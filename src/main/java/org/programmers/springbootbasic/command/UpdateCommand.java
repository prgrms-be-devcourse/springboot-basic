package org.programmers.springbootbasic.command;

import org.programmers.springbootbasic.exception.NoIdException;
import org.programmers.springbootbasic.exception.NotUpdateException;
import org.programmers.springbootbasic.io.Console;
import org.programmers.springbootbasic.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class UpdateCommand implements Command{
    private static final Logger logger = LoggerFactory.getLogger(UpdateCommand.class);

    @Override
    public boolean execute(Console console, VoucherService voucherService) {
        try {
            String voucherId = console.input("Type Change VoucherId");
            long value = Long.parseLong(console.input("Type Change value"));
            voucherService.updateVoucher(UUID.fromString(voucherId), value);
            console.printSuccessUpdateMessage();
        } catch (NumberFormatException e) {
            logger.error("NumberFormat Exception 입니다", e);
        } catch (IllegalArgumentException e) {
            logger.error("잘못된 입력 입니다.", e);
        } catch (NotUpdateException e) {
            logger.error("업데이트에 실패하였습니다.", e);
        } catch (NoIdException e) {
            logger.error("바우처를 찾을 수 없습니다.");
        }
        return true;
    }
}
