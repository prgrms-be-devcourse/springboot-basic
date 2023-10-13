package com.programmers.springbootbasic.common.handler;

import com.programmers.springbootbasic.common.response.model.CommonResult;
import com.programmers.springbootbasic.common.utils.ConsoleIOManager;
import com.programmers.springbootbasic.domain.customer.controller.BlacklistCustomerController;
import com.programmers.springbootbasic.domain.voucher.controller.VoucherController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class CommandHandler {
    private final ConsoleIOManager consoleIOManager;
    private final VoucherController voucherController;
    private final BlacklistCustomerController blacklistCustomerController;

    public void run() {
        CommandType command;
        consoleIOManager.printProgramSelectMenu();
        try {
            command = CommandType.from(consoleIOManager.getInput());
            switch (command) {
                case Voucher -> voucherProgram();
                case Customer -> customerProgram();
                case Exit -> consoleIOManager.printSystemMsg("종료합니다.");
                default -> consoleIOManager.printSystemMsg("잘못된 메뉴 선택입니다.");
            }
        } catch (IOException e) {
            consoleIOManager.printSystemMsg("잘못된 입력 오류입니다.");
        }
    }

    private void voucherProgram() {
        CommandType command = CommandType.Init;
        do {
            consoleIOManager.printVoucherProgramMenu();
            try {
                command = CommandType.from(consoleIOManager.getInput());
                switch (command) {
                    case Create -> create();
                    case List -> list();
                    case Exit -> consoleIOManager.printSystemMsg("종료합니다.");
                    default -> consoleIOManager.printSystemMsg("잘못된 메뉴 선택입니다.");
                }
            } catch (IOException e) {
                consoleIOManager.printSystemMsg("잘못된 입력 오류입니다.");
            }
        } while (!command.equals(CommandType.Exit));
    }

    private void customerProgram() {
        CommandType command = CommandType.Init;
        do {
            consoleIOManager.printCustomerProgramMenu();
            try {
                command = CommandType.from(consoleIOManager.getInput());
                switch (command) {
                    case Blacklist -> blacklist();
                    case Exit -> consoleIOManager.printSystemMsg("종료합니다.");
                    default -> consoleIOManager.printSystemMsg("잘못된 메뉴 선택입니다.");
                }
            } catch (IOException e) {
                consoleIOManager.printSystemMsg("잘못된 입력 오류입니다.");
            }
        } while (!command.equals(CommandType.Exit));
    }

    private void create() throws IOException {
        consoleIOManager.printVoucherSelectMenu();
        String voucherType = consoleIOManager.getInput();
        consoleIOManager.println("Type Value of Voucher");
        String value = consoleIOManager.getInput();
        CommonResult commonResult = voucherController.createVoucher(voucherType, value);
        consoleIOManager.printSystemMsg(commonResult.getMessage());
    }

    private void list() {
        voucherController.findAllVoucher()
                .getData()
                .forEach(consoleIOManager::println);
    }

    private void blacklist() {
        blacklistCustomerController.findAllBlacklistCustomer()
                .getData()
                .forEach(consoleIOManager::println);
    }

}
