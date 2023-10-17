package com.programmers.springbootbasic.common.handler;

import com.programmers.springbootbasic.common.response.model.CommonResult;
import com.programmers.springbootbasic.common.utils.ConsoleIOManager;
import com.programmers.springbootbasic.domain.customer.controller.CustomerController;
import com.programmers.springbootbasic.domain.voucher.controller.VoucherController;
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
                    case create -> voucherCreate();
                    case list -> voucherList();
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
                    case create -> customerCreate();
                    case list -> customerList();
                    case blacklist -> blacklist();
                    case addBlacklist -> addBlacklist();
                    case removeBlacklist -> removeBlacklist();
                    case deleteAll -> deleteAllCustomer();
                    case exit -> consoleIOManager.printSystemMsg("종료합니다.");
                    default -> consoleIOManager.printSystemMsg("잘못된 메뉴 선택입니다.");
                }
            } catch (IOException e) {
                log.error(e.toString());
            }
        } while (!command.equals(CommandType.exit));
    }

    private void voucherCreate() throws IOException {
        consoleIOManager.printVoucherSelectMenu();
        String voucherType = consoleIOManager.getInput();
        consoleIOManager.println("Type Value of Voucher");
        String value = consoleIOManager.getInput();
        CommonResult commonResult = voucherController.createVoucher(voucherType, value);
        consoleIOManager.printSystemMsg(commonResult.getMessage());
    }

    private void voucherList() {
        voucherController.findAllVoucher()
                .getData()
                .forEach(consoleIOManager::println);
    }

    private void customerCreate() throws IOException {
        consoleIOManager.print("Enter Email ");
        String email = consoleIOManager.getInput();
        consoleIOManager.print("Enter name ");
        String name = consoleIOManager.getInput();
        CommonResult commonResult = customerController.createCustomer(email, name);
        consoleIOManager.printSystemMsg(commonResult.getMessage());
    }

    private void customerList() {
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

    private void deleteAllCustomer() {
        CommonResult commonResult = customerController.deleteAllCustomer();
        consoleIOManager.printSystemMsg(commonResult.getMessage());
    }

}
