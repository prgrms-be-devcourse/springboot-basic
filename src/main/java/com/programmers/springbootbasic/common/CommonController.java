package com.programmers.springbootbasic.common;

import com.programmers.springbootbasic.common.response.model.CommonResult;
import com.programmers.springbootbasic.common.utils.ConsoleIOManager;
import com.programmers.springbootbasic.domain.voucher.controller.VoucherController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class CommonController {
    private final ConsoleIOManager consoleIOManager;
    private final VoucherController voucherController;

    public void run() {
        String input = "";
        do {
            consoleIOManager.printProgramMenu();
            try {
                input = consoleIOManager.getInput();
                switch (input) {
                    case "create" -> create();
                    case "list" -> list();
                    case "exit" -> consoleIOManager.printSystemMsg("종료합니다.");
                    default -> consoleIOManager.printSystemMsg("잘못된 메뉴 선택입니다.");
                }
            } catch (IOException e) {
                consoleIOManager.printSystemMsg("잘못된 입력 오류입니다.");
            }
        } while (!input.equals("exit"));
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
}
