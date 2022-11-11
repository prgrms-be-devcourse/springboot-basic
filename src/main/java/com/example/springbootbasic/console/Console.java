package com.example.springbootbasic.console;

import com.example.springbootbasic.console.input.ConsoleInput;
import com.example.springbootbasic.console.output.ConsoleOutput;
import com.example.springbootbasic.controller.request.RequestBody;
import com.example.springbootbasic.controller.response.ResponseBody;
import com.example.springbootbasic.dto.CustomerDto;
import com.example.springbootbasic.dto.VoucherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.springbootbasic.console.ConsoleStatus.*;
import static com.example.springbootbasic.console.message.ConsoleMenuMessage.*;

@Component
public class Console {
    private final ConsoleInput consoleInput;
    private final ConsoleOutput consoleOutput;
    private final ConsoleManager consoleManager;

    @Autowired
    public Console(ConsoleInput consoleInput, ConsoleOutput consoleOutput, ConsoleManager consoleManager) {
        this.consoleInput = consoleInput;
        this.consoleOutput = consoleOutput;
        this.consoleManager = consoleManager;
    }

    public ConsoleStatus process() {
        ConsoleStatus status = CONTINUE;
        consoleOutput.printConsole(VOUCHER_MENU_MESSAGE.message());
        consoleOutput.printConsole(CUSTOMER_MENU_MESSAGE.message());
        ConsoleType consoleType = consoleInput.inputCommand();
        switch (consoleType) {
            case VOUCHER_CREATE -> saveVoucherButton();
            case VOUCHER_LIST -> selectAllVouchersButton();
            case CUSTOMER_BLACK_LIST -> selectAllBlackCustomersButton();
            case EXIT -> status = END;
        }
        return status;
    }

    private void selectAllVouchersButton() {
        ResponseBody<List<VoucherDto>> response = consoleManager.selectAllVouchers();
        consoleOutput.printVouchers(response);
    }

    private void saveVoucherButton() {
        consoleOutput.printConsole(VOUCHER_CREATE_MENU_MESSAGE.message());
        RequestBody<VoucherDto> request = consoleInput.inputVoucherData();
        ResponseBody<VoucherDto> response = consoleManager.saveVoucher(request);
        consoleOutput.printSaveVoucher(response);
    }

    private void selectAllBlackCustomersButton() {
        ResponseBody<List<CustomerDto>> response = consoleManager.selectAllBlackCustomers();
        consoleOutput.printCustomers(response);
    }
}
