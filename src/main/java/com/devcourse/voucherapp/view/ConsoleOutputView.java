package com.devcourse.voucherapp.view;

import com.devcourse.voucherapp.entity.Menu;
import com.devcourse.voucherapp.entity.VoucherType;
import org.springframework.stereotype.Component;

@Component
public class ConsoleOutputView implements OutputView {

    private static final String MENU_TITLE = "[할인권 프로그램 v1.0]";
    private static final String INPUT_MESSAGE = "입력 : ";
    private static final String QUIT_MESSAGE = "프로그램을 종료합니다.";
    private static final String VOUCHER_TYPE_SELECTION_MESSAGE = "할인 방식을 선택하세요.";

    @Override
    public void showMenu() {
        printWithLineBreak(MENU_TITLE);

        for (Menu menu : Menu.values()) {
            printWithLineBreak(menu.toString());
        }

        printWithLineBreak();
        printWithoutLineBreak(INPUT_MESSAGE);
    }

    @Override
    public void showQuitMessage() {
        printWithLineBreak();
        printWithLineBreak(QUIT_MESSAGE);
    }

    @Override
    public void showVoucherTypes() {
        printWithLineBreak();
        printWithLineBreak(VOUCHER_TYPE_SELECTION_MESSAGE);

        for (VoucherType type : VoucherType.values()) {
            printWithLineBreak(type.toString());
        }

        printWithoutLineBreak(INPUT_MESSAGE);
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
