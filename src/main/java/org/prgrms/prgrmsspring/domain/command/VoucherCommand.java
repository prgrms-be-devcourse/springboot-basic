package org.prgrms.prgrmsspring.domain.command;

import org.prgrms.prgrmsspring.console.ConsoleIOManager;
import org.prgrms.prgrmsspring.console.VoucherConsole;

import java.util.function.Consumer;

public enum VoucherCommand implements Command {
    CREATE("create a new voucher.", VoucherConsole::create),
    UPDATE("update a voucher.", VoucherConsole::update),
    DELETE("delete a voucher.", VoucherConsole::delete),
    LIST("list vouchers.", VoucherConsole::findAll);

    private final String document;
    private final Consumer<VoucherConsole> consumer;

    VoucherCommand(String document, Consumer<VoucherConsole> consumer) {
        this.document = document;
        this.consumer = consumer;
    }

    public static VoucherCommand from(String commandName) {
        return valueOf(commandName.toUpperCase());
    }

    @Override
    public String getDocument() {
        return document;
    }

    @Override
    public void run(ConsoleIOManager consoleIOManager) {
        this.consumer.accept((VoucherConsole) consoleIOManager);
    }
}
