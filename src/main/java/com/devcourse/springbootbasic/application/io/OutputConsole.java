package com.devcourse.springbootbasic.application.io;

import com.devcourse.springbootbasic.application.constant.Message;
import com.devcourse.springbootbasic.application.domain.Voucher;
import com.devcourse.springbootbasic.application.dto.ListMenu;
import com.devcourse.springbootbasic.application.dto.Menu;
import com.devcourse.springbootbasic.application.dto.VoucherType;
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
        textTerminal.println(Message.START_GAME_PROMPT.getMessageText());

        Arrays.stream(Menu.values())
                .forEach(menu -> {
                    textTerminal.print("Type ");
                    textTerminal.print(menu.getMenuCommand());
                    textTerminal.print(MessageFormat.format("({0}) ", menu.getMenuOrdinal()));
                    textTerminal.println(menu.getMenuPrompt());
                });
    }

    public void showVoucherType() {
        textTerminal.println(Message.VOUCHER_TYPE_PROMPT.getMessageText());

        Arrays.stream(VoucherType.values())
                .forEach(voucherType -> {
                    textTerminal.print(MessageFormat.format("{0}: ", voucherType.getTypeOrdinal()));
                    textTerminal.println(voucherType.getTypeString());
                });
    }

    public void showListMenu() {
        textTerminal.println(Message.LIST_MENU_PROMPT.getMessageText());

        Arrays.stream(ListMenu.values())
                .forEach(listMenu -> {
                    textTerminal.print(MessageFormat.format("{0}: ", listMenu.getListMenuOrdinal()));
                    textTerminal.println(listMenu.getListMenuPrompt());
                });
    }

    public void printMessage(String message) {
        textTerminal.println(message);
    }

    public void printMessage(String message, String messagePostfix) {
        textTerminal.println(MessageFormat.format("{0}{1}", message, messagePostfix));
    }

    public void printError(Exception e) {
        printMessage(e.getMessage());
    }

    public void endPlatform() {
        printMessage(Message.END_GAME_PROMPT.getMessageText());
    }

    public void printList(String message, List<String> list) {
        printMessage(message);
        list.forEach(this::printMessage);
        textTerminal.println();
    }

}
