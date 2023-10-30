package org.prgrms.prgrmsspring.domain.command;

import org.prgrms.prgrmsspring.console.ConsoleIOManager;
import org.prgrms.prgrmsspring.console.WalletConsole;

import java.util.function.Consumer;

public enum WalletCommand implements Command {
    CREATE("allocate voucher to specific customer", WalletConsole::create),
    FIND_VOUCHER("find voucher by customer", WalletConsole::findCustomerVouchers),
    DELETE("delete all vouchers by customer", WalletConsole::deleteCustomerVouchers),
    FIND_CUSTOMER("find customer who having voucher", WalletConsole::findCustomerHasVoucher);

    private final String document;
    private final Consumer<WalletConsole> consumer;

    WalletCommand(String document, Consumer<WalletConsole> consumer) {
        this.document = document;
        this.consumer = consumer;
    }

    @Override
    public String getDocument() {
        return document;
    }

    @Override
    public void run(ConsoleIOManager consoleIOManager) {
        this.consumer.accept((WalletConsole) consoleIOManager);
    }
}
