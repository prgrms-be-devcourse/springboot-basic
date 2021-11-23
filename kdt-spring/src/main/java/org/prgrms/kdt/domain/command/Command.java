package org.prgrms.kdt.domain.command;

import org.prgrms.kdt.domain.command.customer.CustomerCreateCommandAction;
import org.prgrms.kdt.domain.command.customer.CustomerListCommandAction;
import org.prgrms.kdt.domain.command.voucher.VoucherCreateCommandAction;
import org.prgrms.kdt.domain.command.voucher.VoucherListCommandAction;
import org.prgrms.kdt.domain.command.wallet.WalletCreateCommandAction;
import org.prgrms.kdt.domain.command.wallet.WalletFindCommandAction;
import org.prgrms.kdt.domain.command.wallet.WalletListCommandAction;
import org.prgrms.kdt.domain.command.wallet.WalletRemoveCommandAction;
import org.prgrms.kdt.io.IO;
import org.prgrms.kdt.service.CustomerService;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.service.WalletService;

import java.io.IOException;
import java.util.Arrays;

public enum Command {
    EXIT("EXIT"), LIST("LIST"), CREATE("CREATE"), REMOVE("REMOVE"), FIND("FIND");

    private final String value;

    Command(String value) {
        this.value = value;
    }


    public String getValue() {
        return value;
    }

    public static void validateCommand(String cmd) {
        if (invalidCommand(cmd))
            throw new IllegalArgumentException("Invalid Command: " + cmd);
    }


    public static void doVoucherAction(VoucherService voucherService, IO io, Command command) throws IOException, IllegalArgumentException {
        switch (command) {
            case EXIT -> {
                CommandAction exitCommandAction = new ExitCommandAction(io);
                exitCommandAction.action();
            }
            case CREATE -> {
                CommandAction create = new VoucherCreateCommandAction(voucherService, io);
                create.action();
            }
            case LIST -> {
                CommandAction list = new VoucherListCommandAction(voucherService, io);
                list.action();
            }
            default -> new IllegalArgumentException("Wrong Command");
        }
    }

    public static void doCustomerAction(CustomerService customerService, IO io, Command command) throws IOException, IllegalArgumentException {
        switch (command) {
            case EXIT -> {
                CommandAction exitCommandAction = new ExitCommandAction(io);
                exitCommandAction.action();
            }
            case CREATE -> {
                CommandAction create = new CustomerCreateCommandAction(customerService, io);
                create.action();
            }
            case LIST -> {
                CommandAction list = new CustomerListCommandAction(customerService, io);
                list.action();
            }
            default -> new IllegalArgumentException("Wrong Command");
        }
    }

    public static void doWalletAction(WalletService walletService, IO io, Command command) throws IOException, IllegalArgumentException {
        switch (command) {
            case EXIT -> {
                CommandAction exitCommandAction = new ExitCommandAction(io);
                exitCommandAction.action();
            }
            case CREATE -> {
                CommandAction create = new WalletCreateCommandAction(walletService, io);
                create.action();
            }
            case LIST -> {
                CommandAction list = new WalletListCommandAction(walletService, io);
                list.action();
            }
            case FIND -> {
                CommandAction find = new WalletFindCommandAction(walletService, io);
                find.action();
            }
            case REMOVE -> {
                CommandAction remove = new WalletRemoveCommandAction(walletService, io);
                remove.action();
            }
            default -> new IllegalArgumentException("Wrong Command");
        }
    }

    private static boolean invalidCommand(String cmd) {
        return Arrays.stream(Command.values())
                .filter(c -> c.getValue().equals(cmd))
                .findFirst()
                .isEmpty();
    }

}
