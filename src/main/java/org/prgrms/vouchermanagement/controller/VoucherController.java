package org.prgrms.vouchermanagement.controller;

import org.prgrms.vouchermanagement.dto.VoucherCreateInfo;
import org.prgrms.vouchermanagement.dto.VoucherUpdateInfo;
import org.prgrms.vouchermanagement.view.Command;
import org.prgrms.vouchermanagement.view.ConsoleInput;
import org.prgrms.vouchermanagement.view.ConsoleOutput;
import org.prgrms.vouchermanagement.voucher.domain.Voucher;
import org.prgrms.vouchermanagement.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class VoucherController {
    private final ConsoleOutput consoleOutput;
    private final ConsoleInput consoleInput;
    private final VoucherService voucherService;

    public VoucherController(ConsoleOutput consoleOutput, ConsoleInput consoleInput, VoucherService voucherService) {
        this.consoleOutput = consoleOutput;
        this.consoleInput = consoleInput;
        this.voucherService = voucherService;
    }

    public void voucher() {
        boolean notExitCommand = true;

        while (notExitCommand) {
            consoleOutput.printVoucherOptionMessage();
            Command command = consoleInput.commandInput();

            switch (command) {
                case CREATE -> createVoucher();
                case LIST -> voucherLists();
                case UPDATE -> updateVoucher();
                case DELETE -> deleteVoucher();
                case EXIT -> notExitCommand = exit();
            }
        }
    }

    private void createVoucher() {
        consoleOutput.printCreateVoucherMessage();
        VoucherCreateInfo voucherInput = consoleInput.createVoucherInput();
        voucherService.createVoucher(voucherInput.policy(), voucherInput.amountOrPercent());
        consoleOutput.printCreateVoucherCompleteMessage();
    }

    private void voucherLists() {
        List<Voucher> vouchers = voucherService.voucherLists();
        consoleOutput.printVouchers(vouchers);
    }

    private void updateVoucher() {
        consoleOutput.printUpdateVoucherMessage();
        VoucherUpdateInfo voucherUpdateInfo = consoleInput.updateVoucherInput();
        voucherService.updateVoucher(voucherUpdateInfo.voucherId(), voucherUpdateInfo.amountOrPercent());
        consoleOutput.printUpdateVoucherCompleteMessage();
    }

    private void deleteVoucher() {
        consoleOutput.printDeleteVoucherMessage();
        voucherService.deleteVoucher();
    }


    private boolean exit() {
        consoleOutput.printExitMessage();
        return false;
    }
}
