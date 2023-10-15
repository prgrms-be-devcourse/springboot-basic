package org.prgrms.vouchermanagement;

import org.prgrms.vouchermanagement.dto.VoucherInfo;
import org.prgrms.vouchermanagement.service.VoucherService;
import org.prgrms.vouchermanagement.view.Command;
import org.prgrms.vouchermanagement.view.ConsoleInput;
import org.prgrms.vouchermanagement.view.ConsoleOutput;
import org.prgrms.vouchermanagement.voucher.Voucher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Controller implements CommandLineRunner {

    private final ConsoleInput consoleInput = new ConsoleInput();
    private final ConsoleOutput consoleOutput = new ConsoleOutput();

    private final VoucherService voucherService;

    public Controller(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public void run(String... args) throws Exception {
        boolean notExitCommand = true;

        while (notExitCommand) {
            consoleOutput.printWelcomeMessage();
            Command command = consoleInput.commandInput();

            switch (command) {
                case CREATE -> createVoucher();
                case LIST -> voucherLists();
                case EXIT -> notExitCommand = exit();
            }
        }
    }

    private void createVoucher() {
        consoleOutput.printCreateVoucherMessage();
        VoucherInfo voucherInput = consoleInput.createVoucherInput();
        voucherService.createVoucher(voucherInput.policy(), voucherInput.amountOrPercent());
        consoleOutput.printCreateVoucherCompleteMessage();
    }

    private void voucherLists() {
        List<Voucher> vouchers = voucherService.voucherLists();
        consoleOutput.printVouchers(vouchers);
    }

    private boolean exit() {
        consoleOutput.printExitMessage();
        return false;
    }
}
