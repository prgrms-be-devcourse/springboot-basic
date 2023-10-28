package com.programmers.springbootbasic.common.handler;

import com.programmers.springbootbasic.common.response.CommonResult;
import com.programmers.springbootbasic.common.utils.ConsoleIOManager;
import com.programmers.springbootbasic.domain.customer.controller.CustomerController;
import com.programmers.springbootbasic.domain.voucher.controller.VoucherController;
import com.programmers.springbootbasic.domain.wallet.controller.WalletController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommandHandler {
    private final ConsoleIOManager consoleIOManager;
    private final VoucherController voucherController;
    private final CustomerController customerController;
    private final WalletController walletController;

    public void run() {
        CommandType command;
        consoleIOManager.println(CommandOutput.PROGRAM_SELECT.getValue());
        try {
            command = CommandType.from(consoleIOManager.getInput());
            switch (command) {
                case VOUCHER -> voucherProgram();
                case CUSTOMER -> customerProgram();
                case EXIT -> consoleIOManager.printSystemMsg(CommandOutput.EXIT.getValue());
                default -> consoleIOManager.printSystemMsg(CommandOutput.WRONG_CHOICE.getValue());
            }
        } catch (IOException e) {
            log.error(e.toString());
        }
    }

    private void voucherProgram() {
        CommandType command = CommandType.INIT;
        do {
            consoleIOManager.println(CommandOutput.VOUCHER_MENU.getValue());
            try {
                command = CommandType.from(consoleIOManager.getInput());
                switch (command) {
                    case CREATE -> createVoucher();
                    case LIST -> listVoucher();
                    case FIND -> findVoucher();
                    case UPDATE -> updateVoucher();
                    case DELETE -> deleteVoucher();
                    case DELETE_ALL -> deleteAllVouchers();
                    case CUSTOMER -> showWalletsByVoucher();
                    case EXIT -> consoleIOManager.printSystemMsg(CommandOutput.EXIT.getValue());
                    default -> consoleIOManager.printSystemMsg(CommandOutput.WRONG_CHOICE.getValue());
                }
            } catch (IOException e) {
                log.error(e.toString());
            }
        } while (!command.equals(CommandType.EXIT));
    }

    private void customerProgram() {
        CommandType command = CommandType.INIT;
        do {
            consoleIOManager.println(CommandOutput.CUSTOMER_MENU.getValue());
            try {
                command = CommandType.from(consoleIOManager.getInput());
                switch (command) {
                    case CREATE -> createCustomer();
                    case LIST -> listCustomers();
                    case BLACKLIST -> blacklist();
                    case ADD_BLACKLIST -> addBlacklist();
                    case REMOVE_BLACKLIST -> removeBlacklist();
                    case DELETE_ALL -> deleteAllCustomers();
                    case ADD_VOUCHER -> addVoucherInWallet();
                    case REMOVE_VOUCHER -> removeVoucherFromWallet();
                    case WALLET -> showWalletByCustomer();
                    case EXIT -> consoleIOManager.printSystemMsg(CommandOutput.EXIT.getValue());
                    default -> consoleIOManager.printSystemMsg(CommandOutput.WRONG_CHOICE.getValue());
                }
            } catch (IOException e) {
                log.error(e.toString());
            }
        } while (!command.equals(CommandType.EXIT));
    }

    private void createVoucher() throws IOException {
        consoleIOManager.println(CommandOutput.VOUCHER_SELECT.getValue());
        String voucherType = consoleIOManager.getInput();
        consoleIOManager.println(CommandOutput.REQUEST_VOUCHER_VALUE.getValue());
        String value = consoleIOManager.getInput();
        CommonResult<String> commonResult = voucherController.createVoucher(voucherType, value);
        consoleIOManager.printSystemMsg(commonResult.getData());
    }

    private void listVoucher() {
        voucherController.findAllVouchers()
                .getData()
                .forEach(consoleIOManager::println);
    }

    private void findVoucher() throws IOException {
        consoleIOManager.print(CommandOutput.REQUEST_VOUCHER_ID.getValue());
        String voucherId = consoleIOManager.getInput();
        CommonResult<String> commonResult = voucherController.findVoucherById(voucherId);
        if (commonResult.isSuccess()) {
            consoleIOManager.println(commonResult.getData());
        } else {
            consoleIOManager.printSystemMsg(commonResult.getData());
        }
    }

    private void updateVoucher() throws IOException {
        consoleIOManager.print(CommandOutput.REQUEST_VOUCHER_ID.getValue());
        String voucherId = consoleIOManager.getInput();
        consoleIOManager.print(CommandOutput.REQUEST_VOUCHER_VALUE.getValue());
        String value = consoleIOManager.getInput();
        CommonResult<String> commonResult = voucherController.updateVoucher(voucherId, value);
        consoleIOManager.printSystemMsg(commonResult.getData());
    }

    private void deleteVoucher() throws IOException {
        consoleIOManager.print(CommandOutput.REQUEST_VOUCHER_ID.getValue());
        String voucherId = consoleIOManager.getInput();
        CommonResult<String> commonResult = voucherController.deleteVoucher(voucherId);
        consoleIOManager.printSystemMsg(commonResult.getData());
    }

    private void deleteAllVouchers() {
        CommonResult<String> commonResult = voucherController.deleteAllVouchers();
        consoleIOManager.printSystemMsg(commonResult.getData());
    }

    private void showWalletsByVoucher() throws IOException {
        consoleIOManager.print(CommandOutput.REQUEST_VOUCHER_ID.getValue());
        String voucherId = consoleIOManager.getInput();
        CommonResult<String> commonResult = walletController.findWalletsByVoucherId(voucherId);
        if (commonResult.isSuccess()) {
            consoleIOManager.println(commonResult.getData());
        } else {
            consoleIOManager.printSystemMsg(commonResult.getData());
        }
    }

    private void createCustomer() throws IOException {
        consoleIOManager.print(CommandOutput.REQUEST_EMAIL.getValue());
        String email = consoleIOManager.getInput();
        consoleIOManager.print(CommandOutput.REQUEST_NAME.getValue());
        String name = consoleIOManager.getInput();
        CommonResult<String> commonResult = customerController.createCustomer(email, name);
        consoleIOManager.printSystemMsg(commonResult.getData());
    }

    private void listCustomers() {
        customerController.findAllCustomer()
                .getData()
                .forEach(consoleIOManager::println);
    }

    private void blacklist() {
        customerController.findAllBlacklistCustomer()
                .getData()
                .forEach(consoleIOManager::println);
    }

    private void addBlacklist() throws IOException {
        consoleIOManager.print(CommandOutput.REQUEST_EMAIL.getValue());
        String email = consoleIOManager.getInput();
        CommonResult<String> commonResult = customerController.addCustomerInBlacklist(email);
        consoleIOManager.printSystemMsg(commonResult.getData());
    }

    private void removeBlacklist() throws IOException {
        consoleIOManager.print(CommandOutput.REQUEST_EMAIL.getValue());
        String email = consoleIOManager.getInput();
        CommonResult<String> commonResult = customerController.removeCustomerInBlacklist(email);
        consoleIOManager.printSystemMsg(commonResult.getData());
    }

    private void deleteAllCustomers() {
        CommonResult<String> commonResult = customerController.deleteAllCustomer();
        consoleIOManager.printSystemMsg(commonResult.getData());
    }

    private void addVoucherInWallet() throws IOException {
        consoleIOManager.print(CommandOutput.REQUEST_EMAIL.getValue());
        String email = consoleIOManager.getInput();
        listVoucher();
        consoleIOManager.print(CommandOutput.REQUEST_VOUCHER_ID.getValue());
        String voucherId = consoleIOManager.getInput();
        CommonResult<String> commonResult = walletController.addWallet(email, voucherId);
        consoleIOManager.printSystemMsg(commonResult.getData());
    }

    private void removeVoucherFromWallet() throws IOException {
        String email = showWalletByCustomer();
        consoleIOManager.print(CommandOutput.REQUEST_VOUCHER_ID.getValue());
        String voucherId = consoleIOManager.getInput();
        CommonResult<String> commonResult = walletController.deleteWallet(email, voucherId);
        consoleIOManager.printSystemMsg(commonResult.getData());
    }

    private String showWalletByCustomer() throws IOException {
        consoleIOManager.print(CommandOutput.REQUEST_EMAIL.getValue());
        String email = consoleIOManager.getInput();
        CommonResult<String> commonResult = walletController.findWalletsByCustomerEmail(email);
        if (commonResult.isSuccess()) {
            consoleIOManager.println(commonResult.getData());
        } else {
            consoleIOManager.printSystemMsg(commonResult.getData());
        }
        return email;
    }
}
