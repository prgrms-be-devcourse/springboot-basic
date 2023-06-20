package com.devcourse.springbootbasic.engine.io;

import com.devcourse.springbootbasic.engine.exception.InvalidDataException;
import com.devcourse.springbootbasic.engine.voucher.Voucher;

public class OutputConsole {

    public void printLine() {
        System.out.println();
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printVouchers(Voucher voucher) {
        System.out.println(voucher.toString());
    }

    public void printError(InvalidDataException e) {
        System.out.println(e.getMessage());
    }

    public void endPlatform() {
        System.out.println("프로그램을 종료합니다.");
    }
}
