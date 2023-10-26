package org.prgrms.vouchermanagement.controller;

import org.prgrms.vouchermanagement.customer.Customer;
import org.prgrms.vouchermanagement.customer.CustomerService;
import org.prgrms.vouchermanagement.dto.VoucherCreateInfo;
import org.prgrms.vouchermanagement.blackCustomer.BlackCustomer;
import org.prgrms.vouchermanagement.blackCustomer.BlackCustomerService;
import org.prgrms.vouchermanagement.dto.VoucherUpdateInfo;
import org.prgrms.vouchermanagement.dto.WalletCreateInfo;
import org.prgrms.vouchermanagement.service.VoucherService;
import org.prgrms.vouchermanagement.view.Command;
import org.prgrms.vouchermanagement.view.ConsoleInput;
import org.prgrms.vouchermanagement.view.ConsoleOutput;
import org.prgrms.vouchermanagement.voucher.Voucher;
import org.prgrms.vouchermanagement.wallet.WalletService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class Controller implements CommandLineRunner {

    private final ConsoleInput consoleInput = new ConsoleInput();
    private final ConsoleOutput consoleOutput = new ConsoleOutput();

    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final BlackCustomerService blackCustomerService;
    private final WalletService walletService;

    public Controller(VoucherService voucherService, CustomerService customerService, BlackCustomerService blackCustomerService, WalletService walletService) {
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.blackCustomerService = blackCustomerService;
        this.walletService = walletService;
    }

    @Override
    public void run(String... args) {
        boolean notExitCommand = true;

        while (notExitCommand) {
            consoleOutput.printWelcomeMessage();
            Command command = consoleInput.commandInput();

            switch (command) {
                case CREATE -> createVoucher();
                case LIST -> voucherLists();
                case CUSTOMER -> showCustomerList();
                case BLACKLIST -> showBlackList();
                case UPDATE -> updateVoucher();
                case DELETE -> deleteVoucher();
                case WALLET -> wallet();
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

    private void showCustomerList() {
        List<Customer> customerList = customerService.showCustomerList();
        consoleOutput.printCustomerList(customerList);
    }

    private void showBlackList() {
        List<BlackCustomer> blackList = blackCustomerService.getBlackList();
        consoleOutput.printBlackList(blackList);
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

    private void wallet() {
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
