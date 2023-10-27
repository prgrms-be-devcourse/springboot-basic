package org.programmers.springorder.controller;

import org.programmers.springorder.console.Console;
import org.programmers.springorder.constant.Message;
import org.programmers.springorder.dto.voucher.VoucherRequestDto;
import org.programmers.springorder.service.VoucherService;
import org.programmers.springorder.utils.ExceptionHandler;
import org.programmers.springorder.utils.VoucherMenuType;
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
        boolean isRunning = true;

        while (isRunning) {
            VoucherMenuType menu = ExceptionHandler.input(Console::inputVoucherMenu);

            switch (menu) {
                case CREATE -> createVoucher();
                case LIST -> getVoucherList();
                case UPDATE -> ExceptionHandler.process(VoucherController::updateVoucher, this);
                case DELETE -> ExceptionHandler.process(VoucherController::deleteVoucher, this);
                case BACK -> {
                    isRunning = false;
                    console.printMessage(Message.BACK_TO_MENU_MESSAGE);
                }
            }
        }
    }

    private void createVoucher() {
        VoucherRequestDto request = console.inputVoucherInfo();
        voucherService.createVoucher(request);
        console.printMessage(Message.VOUCHER_REGISTERED);
    }

    private void getVoucherList() {
        console.showVoucherList(voucherService.getAllVoucher());
    }

    private void updateVoucher() {
        UUID voucherId = UUID.fromString(console.inputVoucherId());
        VoucherRequestDto request = console.inputVoucherInfo();
        voucherService.updateVoucher(voucherId, request);
        console.printMessage(Message.VOUCHER_UPDATED);
    }

    private void deleteVoucher() {
        UUID voucherId = UUID.fromString(console.inputVoucherId());
        voucherService.deleteVoucher(voucherId);
        console.printMessage(Message.VOUCHER_DELETED);
    }


}
