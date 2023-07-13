package com.programmers.voucher.global.io.menu;

import com.programmers.voucher.domain.voucher.controller.VoucherConsoleController;
import com.programmers.voucher.domain.voucher.dto.VoucherDto;
import com.programmers.voucher.domain.voucher.dto.request.VoucherCreateRequest;
import com.programmers.voucher.global.io.Console;
import com.programmers.voucher.global.io.command.VoucherCommandType;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

import static com.programmers.voucher.global.util.ConsoleMessages.CREATED_NEW_VOUCHER;
import static com.programmers.voucher.global.util.ConsoleMessages.DELETED_VOUCHER;

@Component
public class ConsoleVoucherMenu {
    private final Console console;
    private final VoucherConsoleController voucherController;

    public ConsoleVoucherMenu(Console console, VoucherConsoleController voucherController) {
        this.console = console;
        this.voucherController = voucherController;
    }

    public void runningVoucherService() {
        console.printVoucherCommandSet();
        boolean run = true;
        while (run) {
            run = voucherMapping();
        }
    }

    private boolean voucherMapping() {
        VoucherCommandType voucherCommandType = console.inputVoucherCommandType();
        switch (voucherCommandType) {
            case CREATE -> {
                VoucherCreateRequest request = console.inputVoucherCreateInfo();
                UUID voucherId = voucherController.createVoucher(request);
                String consoleMessage = MessageFormat.format(CREATED_NEW_VOUCHER, voucherId.toString());
                console.print(consoleMessage);
            }
            case LIST -> {
                List<VoucherDto> vouchers = voucherController.findVouchers();
                console.printVouchers(vouchers);
            }
            case DELETE -> {
                UUID voucherId = console.inputUUID();
                voucherController.deleteVoucher(voucherId);
                console.print(DELETED_VOUCHER);
            }
            case HELP -> {
                console.printVoucherCommandSet();
            }
            case EXIT -> {
                return false;
            }
        }
        return true;
    }
}
