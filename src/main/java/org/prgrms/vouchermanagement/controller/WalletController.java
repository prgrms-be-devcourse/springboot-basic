package org.prgrms.vouchermanagement.controller;

import org.prgrms.vouchermanagement.dto.WalletCreateInfo;
import org.prgrms.vouchermanagement.view.Command;
import org.prgrms.vouchermanagement.view.ConsoleInput;
import org.prgrms.vouchermanagement.view.ConsoleOutput;
import org.prgrms.vouchermanagement.voucher.domain.Voucher;
import org.prgrms.vouchermanagement.wallet.service.WalletService;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class WalletController {
    private final ConsoleOutput consoleOutput;
    private final ConsoleInput consoleInput;
    private final WalletService walletService;

    public WalletController(ConsoleOutput consoleOutput, ConsoleInput consoleInput, WalletService walletService) {
        this.consoleOutput = consoleOutput;
        this.consoleInput = consoleInput;
        this.walletService = walletService;
    }

    public void wallet() {
        boolean notExitCommand = true;

        while (notExitCommand) {
            consoleOutput.printWalletMessage();
            Command command = consoleInput.commandInput();

            switch (command) {
                case CREATE -> createWallet();
                case LIST -> findVouchersByCustomerId();
                case DELETE -> deleteVoucherByCustomerId();
                case FIND -> findCustomerByVoucherId();
                case EXIT -> notExitCommand = exit();
            }
        }
    }

    private void createWallet() {
        consoleOutput.printCreateWalletMessage();
        WalletCreateInfo walletCreateInfo = consoleInput.createWalletInput();
        walletService.create(walletCreateInfo);
    }

    private void findVouchersByCustomerId() {
        consoleOutput.printAllVouchersByCustomerId();
        UUID customerId = consoleInput.findAllVouchersByCustomerId();
        List<Voucher> voucherList = walletService.findVouchers(customerId);
        consoleOutput.printVouchers(voucherList);
    }

    private void deleteVoucherByCustomerId() {
        consoleOutput.printDeleteVoucherByCustomerId();
        UUID customerId = consoleInput.deleteVoucherByCustomerId();
        walletService.delete(customerId);
    }

    private void findCustomerByVoucherId() {
        consoleOutput.printCustomerByVoucherId();
        UUID voucherId = consoleInput.findCustomerByVoucherId();
        consoleOutput.printCustomer(walletService.findCustomer(voucherId));
    }


    private boolean exit() {
        consoleOutput.printExitMessage();
        return false;
    }
}
