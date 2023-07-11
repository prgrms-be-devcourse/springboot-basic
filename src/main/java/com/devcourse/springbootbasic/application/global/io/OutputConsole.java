package com.devcourse.springbootbasic.application.global.io;

import com.devcourse.springbootbasic.application.global.model.CommandMenu;
import com.devcourse.springbootbasic.application.global.model.DomainMenu;
import com.devcourse.springbootbasic.application.global.model.PropertyMenu;
import com.devcourse.springbootbasic.application.voucher.model.VoucherType;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Component
public class OutputConsole {

    private static final TextIO textIO = TextIoFactory.getTextIO();
    private static final TextTerminal<?> textTerminal = textIO.getTextTerminal();

    public void showCommandMenu() {
        textTerminal.println(OutputMessage.START_GAME.getMessageText());

        Arrays.stream(CommandMenu.values())
                .forEach(menu -> textTerminal.println(menu.getMenuPrompt()));
    }

    public void showDomainMenu() {
        textTerminal.println(OutputMessage.DOMAIN_MENU.getMessageText());

        Arrays.stream(DomainMenu.values())
                .forEach(domainMenu -> textTerminal.println(MessageFormat.format("{0}: {1}", domainMenu.ordinal(), domainMenu.name())));
    }

    public void showPropertyMenu(Stream<PropertyMenu> stream) {
        textTerminal.println(OutputMessage.PROPERTY_MENU.getMessageText());
        stream.forEach(propertyMenu -> textTerminal.println(MessageFormat.format("{0}: {1}", propertyMenu.ordinal(), propertyMenu.name())));
    }

    public void showVoucherType() {
        textTerminal.println(OutputMessage.VOUCHER_TYPE.getMessageText());

        Arrays.stream(VoucherType.values())
                .forEach(voucherType -> textTerminal.print(MessageFormat.format("{0}: {1}\n", voucherType.ordinal(), voucherType.name())));
    }

    public void printMessage(String message) {
        textTerminal.println(message);
    }

    public void printError(Exception e) {
        printMessage(e.getMessage());
        e.printStackTrace();
    }

    public void closePlatform() {
        printMessage(OutputMessage.END_GAME.getMessageText());
    }

    public void printList(List<String> list) {
        list.forEach(this::printMessage);
        textTerminal.println();
    }

}
