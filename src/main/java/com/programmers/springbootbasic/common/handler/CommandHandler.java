package com.programmers.springbootbasic.common.handler;

import com.programmers.springbootbasic.common.response.model.CommonResult;
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
        consoleIOManager.printProgramSelectMenu();
        try {
            command = CommandType.from(consoleIOManager.getInput());
            switch (command) {
                case voucher -> voucherProgram();
                case customer -> customerProgram();
                case exit -> consoleIOManager.printSystemMsg("종료합니다.");
                default -> consoleIOManager.printSystemMsg("잘못된 메뉴 선택입니다.");
            }
        } catch (IOException e) {
            log.error(e.toString());
        }
    }

    private void voucherProgram() {
        CommandType command = CommandType.init;
        do {
            consoleIOManager.printVoucherProgramMenu();
            try {
                command = CommandType.from(consoleIOManager.getInput());
                switch (command) {
                    case create -> createVoucher();
                    case list -> listVoucher();
                    case find -> findVoucher();
                    case update -> updateVoucher();
                    case delete -> deleteVoucher();
                    case deleteAll -> deleteAllVouchers();
                    case customer -> showWalletsByVoucher();
                    case exit -> consoleIOManager.printSystemMsg("종료합니다.");
                    default -> consoleIOManager.printSystemMsg("잘못된 메뉴 선택입니다.");
                }
            } catch (IOException e) {
                log.error(e.toString());
            }
        } while (!command.equals(CommandType.exit));
    }

    private void customerProgram() {
        CommandType command = CommandType.init;
        do {
            consoleIOManager.printCustomerProgramMenu();
            try {
                command = CommandType.from(consoleIOManager.getInput());
                switch (command) {
                    case create -> createCustomer();
                    case list -> listCustomers();
                    case blacklist -> blacklist();
                    case addBlacklist -> addBlacklist();
                    case removeBlacklist -> removeBlacklist();
                    case deleteAll -> deleteAllCustomers();
                    case addVoucher -> addVoucherInWallet();
                    case removeVoucher -> removeVoucherFromWallet();
                    case wallet -> showWalletByCustomer();
                    case exit -> consoleIOManager.printSystemMsg("종료합니다.");
                    default -> consoleIOManager.printSystemMsg("잘못된 메뉴 선택입니다.");
                }
            } catch (IOException e) {
                log.error(e.toString());
            }
        } while (!command.equals(CommandType.exit));
    }

    private void createVoucher() throws IOException {
        consoleIOManager.printVoucherSelectMenu();
        String voucherType = consoleIOManager.getInput();
        consoleIOManager.println("Enter Value of Voucher ");
        String value = consoleIOManager.getInput();
        CommonResult commonResult = voucherController.createVoucher(voucherType, value);
        consoleIOManager.printSystemMsg(commonResult.getMessage());
    }

    private void listVoucher() {
        voucherController.findAllVouchers()
                .getData()
                .forEach(consoleIOManager::println);
    }

    private void findVoucher() throws IOException {
        consoleIOManager.print("Enter Voucher ID ");
        String voucherId = consoleIOManager.getInput();
        CommonResult commonResult = voucherController.findVoucherById(voucherId);
        if (commonResult.isSuccess()) {
            consoleIOManager.println(commonResult.getMessage());
        } else {
            consoleIOManager.printSystemMsg(commonResult.getMessage());
        }
    }

    private void updateVoucher() throws IOException {
        consoleIOManager.print("Enter Voucher ID ");
        String voucherId = consoleIOManager.getInput();
        consoleIOManager.print("Enter Value of Voucher ");
        String value = consoleIOManager.getInput();
        CommonResult commonResult = voucherController.updateVoucher(voucherId, value);
        consoleIOManager.printSystemMsg(commonResult.getMessage());
    }

    private void deleteVoucher() throws IOException {
        consoleIOManager.print("Enter Voucher ID ");
        String voucherId = consoleIOManager.getInput();
        CommonResult commonResult = voucherController.deleteVoucher(voucherId);
        consoleIOManager.printSystemMsg(commonResult.getMessage());
    }

    private void deleteAllVouchers() {
        CommonResult commonResult = voucherController.deleteAllVouchers();
        consoleIOManager.printSystemMsg(commonResult.getMessage());
    }

    private void showWalletsByVoucher() throws IOException {
        consoleIOManager.print("Enter Voucher ID ");
        String voucherId = consoleIOManager.getInput();
        CommonResult commonResult = walletController.findWalletsByVoucherId(voucherId);
        if (commonResult.isSuccess()) {
            consoleIOManager.println(commonResult.getMessage());
        } else {
            consoleIOManager.printSystemMsg(commonResult.getMessage());
        }
    }

    private void createCustomer() throws IOException {
        consoleIOManager.print("Enter Email ");
        String email = consoleIOManager.getInput();
        consoleIOManager.print("Enter name ");
        String name = consoleIOManager.getInput();
        CommonResult commonResult = customerController.createCustomer(email, name);
        consoleIOManager.printSystemMsg(commonResult.getMessage());
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
        consoleIOManager.print("Enter Email ");
        String email = consoleIOManager.getInput();
        CommonResult commonResult = customerController.addCustomerInBlacklist(email);
        consoleIOManager.printSystemMsg(commonResult.getMessage());
    }

    private void removeBlacklist() throws IOException {
        consoleIOManager.print("Enter Email ");
        String email = consoleIOManager.getInput();
        CommonResult commonResult = customerController.removeCustomerInBlacklist(email);
        consoleIOManager.printSystemMsg(commonResult.getMessage());
    }

    private void deleteAllCustomers() {
        CommonResult commonResult = customerController.deleteAllCustomer();
        consoleIOManager.printSystemMsg(commonResult.getMessage());
    }

    private void addVoucherInWallet() throws IOException {
        consoleIOManager.print("Enter Email ");
        String email = consoleIOManager.getInput();
        listVoucher();
        consoleIOManager.print("Enter Voucher ID ");
        String voucherId = consoleIOManager.getInput();
        CommonResult commonResult = walletController.addWallet(email, voucherId);
        consoleIOManager.printSystemMsg(commonResult.getMessage());
    }

    private void removeVoucherFromWallet() throws IOException {
        String email = showWalletByCustomer();
        consoleIOManager.print("Enter Voucher ID ");
        String voucherId = consoleIOManager.getInput();
        CommonResult commonResult = walletController.deleteWallet(email, voucherId);
        consoleIOManager.printSystemMsg(commonResult.getMessage());
    }

    private String showWalletByCustomer() throws IOException {
        consoleIOManager.print("Enter Email ");
        String email = consoleIOManager.getInput();
        CommonResult commonResult = walletController.findWalletsByCustomerEmail(email);
        if (commonResult.isSuccess()) {
            consoleIOManager.println(commonResult.getMessage());
        } else {
            consoleIOManager.printSystemMsg(commonResult.getMessage());
        }
        return email;
    }
}
