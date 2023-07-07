package com.devcourse.springbootbasic.application.global.io;

import com.devcourse.springbootbasic.application.global.model.ListMenu;
import com.devcourse.springbootbasic.application.global.model.Menu;
import com.devcourse.springbootbasic.application.voucher.vo.VoucherType;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

@Component
public class OutputConsole {

    private static final TextIO textIO = TextIoFactory.getTextIO();
    private static final TextTerminal<?> textTerminal = textIO.getTextTerminal();

    public void showMenu() {
        textTerminal.println(OutputMessage.START_GAME_PROMPT.getMessageText());

        Arrays.stream(Menu.values())
                .forEach(menu -> {
                    textTerminal.print("Type ");
                    textTerminal.print(menu.getMenuCommand());
                    textTerminal.print(MessageFormat.format("({0}) ", menu.getMenuOrdinal()));
                    textTerminal.println(menu.getMenuPrompt());
                });
    }

    public void showVoucherType() {
        textTerminal.println(OutputMessage.VOUCHER_TYPE_PROMPT.getMessageText());

        Arrays.stream(VoucherType.values())
                .forEach(voucherType -> {
                    textTerminal.print(MessageFormat.format("{0}: ", voucherType.getTypeOrdinal()));
                    textTerminal.println(voucherType.getTypeString());
                });
    }

    public void showListMenu() {
        textTerminal.println(OutputMessage.LIST_MENU_PROMPT.getMessageText());

        Arrays.stream(ListMenu.values())
                .forEach(listMenu -> {
                    textTerminal.print(MessageFormat.format("{0}: ", listMenu.getListMenuOrdinal()));
                    textTerminal.println(listMenu.getListMenuPrompt());
                });
    }

    public void printMessage(String message) {
        textTerminal.println(message);
    }

    public void printError(Exception e) {
        printMessage(e.getMessage());
        e.printStackTrace();
    }

    public void closePlatform() {
        printMessage(OutputMessage.END_GAME_PROMPT.getMessageText());
    }

    public void printList(String message, List<String> list) {
        printMessage(message);
        list.forEach(this::printMessage);
        textTerminal.println();
    }

}
