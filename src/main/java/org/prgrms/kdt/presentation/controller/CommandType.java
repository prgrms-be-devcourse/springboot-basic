package org.prgrms.kdt.presentation.controller;

import org.prgrms.kdt.presentation.io.ConsoleIO;
import org.prgrms.kdt.service.BlackListService;
import org.prgrms.kdt.service.VoucherService;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CommandType {
    EXIT("exit", "Type 'exit' to exit the program.") {
        @Override
        public boolean executeCommand(ConsoleIO consoleIO, VoucherService voucherService, BlackListService blackListService) {
            consoleIO.terminate();
            return false;
        }
    },
    CREATE("create", "Type 'create' to create a new voucher.") {
        @Override
        public boolean executeCommand(ConsoleIO consoleIO, VoucherService voucherService, BlackListService blackListService) {
            String voucherType = consoleIO.inputVoucherType();
            String voucherDiscountValue = consoleIO.inputVoucherDiscountValue();
            voucherService.create(voucherType, voucherDiscountValue);
            return true;
        }
    },
    LIST("list", "Type 'list' to list all vouchers.") {
        @Override
        public boolean executeCommand(ConsoleIO consoleIO, VoucherService voucherService, BlackListService blackListService) {
            consoleIO.printItems(voucherService.getAllVouchers());
            return true;
        }
    },
    BLACKLIST("blacklist", "Type 'blacklist' to list all blacklist.") {
        @Override
        public boolean executeCommand(ConsoleIO consoleIO, VoucherService voucherService, BlackListService blackListService) {
            consoleIO.printItems(blackListService.getAllBlackList());
            return true;
        }
    };

    public final String command;
    private final String expression;

    CommandType(String command, String expression) {
        this.command = command;
        this.expression = expression;
    }

    public static CommandType of(String cmd) {
        return Stream.of(values())
                .filter(cmdStatus -> cmdStatus.command.equals(cmd))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 Command입니다."));
    }

    public static String getAllCommandExpression() {
        return Stream.of(values())
                .map(cmdStat -> cmdStat.expression)
                .collect(Collectors.joining("\n"));
    }

    public abstract boolean executeCommand(ConsoleIO consoleIO, VoucherService voucherService, BlackListService blackListService);
}
