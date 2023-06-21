package com.devcourse.springbootbasic.engine.io;

import com.devcourse.springbootbasic.engine.exception.InvalidDataException;
import com.devcourse.springbootbasic.engine.voucher.Voucher;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

public class OutputConsole {

    private TextTerminal output = TextIoFactory.getTextTerminal();

    public void printLine() {
        output.println();
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
