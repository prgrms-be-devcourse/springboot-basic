package com.devcourse.voucherapp.view;

import com.devcourse.voucherapp.entity.Menu;
import org.springframework.stereotype.Component;

@Component
public class ConsoleOutputView implements OutputView {

    private static final String MENU_TITLE = "[할인권 프로그램 v1.0]";
    private static final String MENU_INPUT_MESSAGE = "메뉴 입력(숫자만) : ";
    private static final String QUIT_MESSAGE = "프로그램을 종료합니다.";

    @Override
    public void showMenu() {
        printWithLineBreak(MENU_TITLE);

        for (Menu menu : Menu.values()) {
            printWithLineBreak(menu.toString());
        }

        printWithLineBreak();
        printWithoutLineBreak(MENU_INPUT_MESSAGE);
    }

    @Override
    public void showQuitMessage() {
        printWithLineBreak();
        printWithLineBreak(QUIT_MESSAGE);
    }

    @Override
    public void printWithLineBreak() {
        System.out.println();
    }

    @Override
    public void printWithLineBreak(String data) {
        System.out.println(data);
    }

    @Override
    public void printWithoutLineBreak(String data) {
        System.out.print(data);
    }
}
