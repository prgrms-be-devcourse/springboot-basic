package org.programmers.springbootbasic.command;

import org.programmers.springbootbasic.io.Console;
import org.programmers.springbootbasic.voucher.service.VoucherService;

import java.util.UUID;

public class DeleteCommand implements Command {

    @Override
    public boolean execute(Console console, VoucherService voucherService) {
        String voucherId = console.input("삭제할 아이디를 입력하세요.");
        voucherService.deleteVoucher(UUID.fromString(voucherId));
        console.printSuccessDeleteMessage();
        return true;
    }
}
