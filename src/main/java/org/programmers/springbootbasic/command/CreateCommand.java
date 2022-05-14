package org.programmers.springbootbasic.command;

import org.programmers.springbootbasic.exception.DuplicateObjectKeyException;
import org.programmers.springbootbasic.exception.NotInsertException;
import org.programmers.springbootbasic.io.Console;
import org.programmers.springbootbasic.voucher.controller.api.CreateVoucherRequest;
import org.programmers.springbootbasic.voucher.service.DefaultVoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(CreateCommand.class);

    @Override
    public boolean execute(Console console, DefaultVoucherService defaultVoucherService) {
        try {
            long value = Long.parseLong(console.input("Type value"));
            String voucherType = (console.input("Choose Voucher type\n FIXED\n PERCENT"));
            defaultVoucherService.createVoucher(new CreateVoucherRequest(value, voucherType));
            console.printSuccessMessage();
        } catch (NumberFormatException e) {
            logger.error("NumberFormat Exception 입니다", e);
        } catch (IllegalArgumentException e) {
            logger.error("잘못된 입력 입니다.", e);
        } catch (NotInsertException e) {
            logger.error("바우처 입력 실패 입니다.", e);
        } catch (DuplicateObjectKeyException e) {
            logger.error("중복된 바우처 값입니다.", e);
        }
        return true;
    }

}
