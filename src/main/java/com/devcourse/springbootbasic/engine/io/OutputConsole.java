package com.devcourse.springbootbasic.engine.io;

import com.devcourse.springbootbasic.engine.exception.InvalidDataException;
import com.devcourse.springbootbasic.engine.voucher.Voucher;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.springframework.stereotype.Component;

@Component
public class OutputConsole {

    private TextTerminal output = TextIoFactory.getTextTerminal();

    public void printLine() {
        output.println();
    }

    public void printMenus() {
        printMessage("=== Voucher Program ===");
        printMessage("Type exit to exit the program.");
        printMessage("Type create to create a new voucher.");
        printMessage("Type list to list all vouchers.");
    }

    public void printMessage(String message) {
        output.println(message);
    }

    public void printVouchers(Voucher voucher) {
        printMessage(voucher.toString());
    }

    public void printError(InvalidDataException e) {
        printMessage(e.getMessage());
    }

    public void endPlatform() {
        printMessage("프로그램을 종료합니다.");
    }
}
