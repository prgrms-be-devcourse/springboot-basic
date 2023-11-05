package org.programmers.springorder.controller.voucher;

import org.programmers.springorder.console.Console;
import org.programmers.springorder.constant.Message;
import org.programmers.springorder.constant.VoucherMenuType;
import org.programmers.springorder.dto.voucher.VoucherRequestDto;
import org.programmers.springorder.global.handler.ExceptionHandler;
import org.programmers.springorder.service.VoucherService;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class VoucherController {
    private final Console console;
    private final VoucherService voucherService;

    public VoucherController(Console console, VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

    public void run() {
        VoucherMenuType menu;
        do {
            menu = ExceptionHandler.input(console::inputVoucherMenu);
            handleMenu(menu);
        } while (!menu.isBack());
    }

    private void handleMenu(VoucherMenuType menu) {
        switch (menu) {
            case CREATE -> createVoucher();
            case LIST -> getVoucherList();
            case UPDATE -> ExceptionHandler.process(VoucherController::updateVoucher, this);
            case DELETE -> ExceptionHandler.process(VoucherController::deleteVoucher, this);
            case BACK -> console.back();
        }
    }

    private void createVoucher() {
        VoucherRequestDto request = ExceptionHandler.input(console::inputVoucherInfo);
        voucherService.createVoucher(request);
        console.printMessage(Message.VOUCHER_REGISTERED);
    }

    private void getVoucherList() {
        console.showVoucherList(voucherService.getAllVoucher());
    }

    private void updateVoucher() {
        UUID voucherId = console.inputVoucherId();
        VoucherRequestDto request = console.inputVoucherInfo();
        voucherService.updateVoucher(voucherId, request);
        console.printMessage(Message.VOUCHER_UPDATED);
    }

    private void deleteVoucher() {
        UUID voucherId = console.inputVoucherId();
        voucherService.deleteVoucher(voucherId);
        console.printMessage(Message.VOUCHER_DELETED);
    }

}
