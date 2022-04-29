package org.programmers.springbootbasic.command;

import org.programmers.springbootbasic.exception.DuplicateObjectKeyException;
import org.programmers.springbootbasic.exception.NotInsertException;
import org.programmers.springbootbasic.io.Console;
import org.programmers.springbootbasic.voucher.model.VoucherType;
import org.programmers.springbootbasic.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreateCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(CreateCommand.class);

    @Override
    public boolean execute(Console console, VoucherService voucherService) {
        try {
            long value = Long.parseLong(console.input("Type value"));
            int voucherNumber = Integer.parseInt(console.input("Type Voucher number\n 1. FixedAmountVoucher\n 2. PercentDiscountVoucher"));
            VoucherType voucherType = VoucherType.findByNumber(voucherNumber);
            voucherService.createVoucher(voucherType, UUID.randomUUID(), value, LocalDateTime.now());
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
