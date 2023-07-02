package com.programmers.console.view;

import com.programmers.console.util.ConsoleMessage;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Console implements InputView, OutputView {

    private static final String ARROW = "> ";

    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public String userInput() {
        return scanner.nextLine().trim().toLowerCase();
    }

    @Override
    public String inputMenu() {
        println(ConsoleMessage.COMMAND_MESSAGE.getMessage());
        print(ARROW);
        return userInput();
    }

    @Override
    public String inputVoucherType() {
        println(ConsoleMessage.CREATE_VOUCHER_TYPE_MESSAGE.getMessage());
        print(ARROW);
        return userInput();
    }

    @Override
    public String inputDiscountValue() {
        println(ConsoleMessage.VOUCHER_DISCOUNT_VALUE_MESSAGE.getMessage());
        print(ARROW);
        return userInput();
    }

    @Override
    public <T> void println(T message) {
        System.out.println(message);
    }

    @Override
    public <T> void print(T message) {
        System.out.print(message);
    }
}
