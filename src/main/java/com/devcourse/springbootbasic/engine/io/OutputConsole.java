package com.devcourse.springbootbasic.engine.io;

import com.devcourse.springbootbasic.engine.config.Message;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OutputConsole {


    public void printLine() {
        System.out.println();
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printError(Exception e) {
        printMessage(e.getMessage());
        printLine();
    }

    public void endPlatform() {
        printMessage(Message.END_GAME);
        printLine();
    }

    public <T> void printVoucher(T t) {
        printMessage(t.toString());
    }

    public <T> void printVouchers(List<T> voucherList) {
        printMessage(Message.LIST_VOUCHERS);
        voucherList.forEach(this::printVoucher);
        printLine();
    }

    public void printBlackCustomers(List<String> blackCustomers) {
        printMessage(Message.BLACK_CUSTOMER);
        blackCustomers.forEach(this::printMessage);
    }
}
