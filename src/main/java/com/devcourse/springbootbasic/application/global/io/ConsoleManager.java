package com.devcourse.springbootbasic.application.global.io;

import com.devcourse.springbootbasic.application.global.model.ListMenu;
import com.devcourse.springbootbasic.application.global.model.Menu;
import com.devcourse.springbootbasic.application.voucher.vo.DiscountValue;
import com.devcourse.springbootbasic.application.voucher.vo.VoucherType;
import com.devcourse.springbootbasic.application.voucher.controller.VoucherDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsoleManager {

    private final InputConsole inputConsole;
    private final OutputConsole outputConsole;

    public ConsoleManager(InputConsole inputConsole, OutputConsole outputConsole) {
        this.inputConsole = inputConsole;
        this.outputConsole = outputConsole;
    }

    public void consoleError(Exception e) {
        outputConsole.printError(e);
    }

    public void consoleClosePlatform() {
        outputConsole.closePlatform();
    }

    public void printList(ListMenu listMenu, List<String> list) {
        outputConsole.printList(listMenu.getListMenuPrompt(), list);
    }

    public Menu consoleMenu() {
        outputConsole.showMenu();
        return inputConsole.readMenu();
    }

    public ListMenu consoleListMenu() {
        outputConsole.showListMenu();
        return inputConsole.readListMenu();
    }

    private VoucherType consoleVoucherType() {
        outputConsole.showVoucherType();
        return inputConsole.readVoucherType();
    }

    private DiscountValue consoleDiscountValue(VoucherType voucherType) {
        return inputConsole.readDiscountValue(voucherType);
    }

    public VoucherDto getVoucherDto() {
        var voucherType = consoleVoucherType();
        var discountValue = consoleDiscountValue(voucherType);
        return new VoucherDto(voucherType, discountValue);
    }

}
