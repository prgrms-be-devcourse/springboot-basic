package org.programmers.springbootbasic.command;

import org.programmers.springbootbasic.exception.NotInsertException;
import org.programmers.springbootbasic.io.Console;
import org.programmers.springbootbasic.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class DeleteCommand implements Command{
    private static final Logger logger = LoggerFactory.getLogger(DeleteCommand.class);

    @Override
    public boolean execute(Console console, VoucherService voucherService) {
        try {
            String voucherId = console.input("삭제할 아이디를 입력하세요.");
            voucherService.deleteVoucher(UUID.fromString(voucherId));
            console.printSuccessDeleteMessage();
        } catch (NotInsertException e) {
            logger.error("존재하지 않는 아이디 입니다.", e);
        }
        return true;
    }
}
