package com.tangerine.voucher_system.application.global.io;

import com.tangerine.voucher_system.application.customer.controller.CustomerMenu;
import com.tangerine.voucher_system.application.global.model.CommandMenu;
import com.tangerine.voucher_system.application.voucher.controller.VoucherMenu;
import com.tangerine.voucher_system.application.voucher.model.VoucherType;
import com.tangerine.voucher_system.application.wallet.controller.WalletMenu;
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

    public void showCommandMenu() {
        textTerminal.println(OutputMessage.START_GAME.getMessageText());

        Arrays.stream(CommandMenu.values())
                .forEach(menu -> textTerminal.println(menu.getMenuPrompt()));
    }

    public void showCustomerMenu() {
        textTerminal.println(OutputMessage.CUSTOMER_MENU.getMessageText());

        Arrays.stream(CustomerMenu.values())
                .forEach(menu -> textTerminal.println(menu.name()));
    }

    public void showVoucherMenu() {
        textTerminal.println(OutputMessage.VOUCHER_MENU.getMessageText());

        Arrays.stream(VoucherMenu.values())
                .forEach(menu -> textTerminal.println(menu.name()));
    }

    public void showWalletMenu() {
        textTerminal.println(OutputMessage.WALLET_MENU.getMessageText());

        Arrays.stream(WalletMenu.values())
                .forEach(menu -> textTerminal.println(menu.name()));
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
