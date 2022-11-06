package com.example.springbootbasic.controller;

import com.example.springbootbasic.console.ConsoleMenu;
import com.example.springbootbasic.console.input.ConsoleInput;
import com.example.springbootbasic.console.input.RequestBody;
import com.example.springbootbasic.console.output.ConsoleOutput;
import com.example.springbootbasic.console.output.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.example.springbootbasic.console.ConsoleMenu.*;
import static com.example.springbootbasic.console.ConsoleStatus.FAIL;

@Component
public class MainController {
    private final ConsoleInput consoleInput;
    private final ConsoleOutput consoleOutput;
    private final VoucherController voucherController;

    @Autowired
    public MainController(ConsoleInput consoleInput, ConsoleOutput consoleOutput, VoucherController voucherController) {
        this.consoleInput = consoleInput;
        this.consoleOutput = consoleOutput;
        this.voucherController = voucherController;
    }

    public ResponseBody executeVoucherProgram() {
        ResponseBody menuResponse = voucherController.selectVoucherMenu();
        consoleOutput.response(menuResponse);

        RequestBody consoleMenuRequest = consoleInput.request();
        Optional<ConsoleMenu> findMenu = findConsoleMenuEnum(consoleMenuRequest);

        ResponseBody responseBody = new ResponseBody();
        if (consoleMenuRequest.getStatus() == FAIL || findMenu.isEmpty()) {
            responseBody.setStatus(FAIL);
            consoleOutput.response(responseBody);
        }
        return executeVoucherMenu(findMenu);
    }

    private Optional<ConsoleMenu> findConsoleMenuEnum(RequestBody consoleMenuRequest) {
        String consoleMenuName = consoleMenuRequest.getBody();
        return findMenu(consoleMenuName);
    }

    private ResponseBody executeVoucherMenu(Optional<ConsoleMenu> findMenu) {
        ResponseBody responseBody = new ResponseBody();
        ConsoleMenu menu = findMenu.get();
        if (menu == EXIT) {
            responseBody = voucherController.shutdownVoucherApplication();
        }
        if (menu == CREATE) {
            responseBody = voucherController.selectHowToCreateVoucher();
            consoleOutput.response(responseBody);
            
            RequestBody voucherInputFormRequest = consoleInput.request();
            responseBody = voucherController.createVoucher(voucherInputFormRequest);
        }
        if (menu == LIST) {
            responseBody = voucherController.selectAllVouchers();
        }

        consoleOutput.response(responseBody);
        return responseBody;
    }
}
