package com.prgms.management.voucher.entity;

import org.beryx.textio.TextIO;

import java.util.Optional;

public enum VoucherType {
    PERCENT("percent", "to create a percent discount voucher.", "Percent") {
        @Override
        public Optional<Voucher> createVoucherFromConsole(TextIO textIO) {
            Integer paramNum = textIO.newIntInputReader()
                    .read(getNextCommand());
            return Optional.of(new PercentDiscountVoucher(paramNum));
        }
    },
    FIXED("fixed", "to create a fixed amount voucher.", "Amount") {
        @Override
        public Optional<Voucher> createVoucherFromConsole(TextIO textIO) {
            Integer paramNum = textIO.newIntInputReader()
                    .read(getNextCommand());
            return Optional.of(new FixedAmountVoucher(paramNum));
        }
    },
    ERROR("error", "this is error command", "none") {
        @Override
        public Optional<Voucher> createVoucherFromConsole(TextIO textIO) {
            return Optional.empty();
        }
    };

    private final String command;
    private final String description;
    private final String nextCommand;

    VoucherType(String command, String description, String nextCommand) {
        this.command = command;
        this.description = description;
        this.nextCommand = nextCommand;
    }

    public static VoucherType of(String command) {
        switch (command.toLowerCase()) {
            case "fixed":
                return FIXED;
            case "percent":
                return PERCENT;
            default:
                return ERROR;
        }
    }

    public String getConsoleScript() {
        return "Type **" + command + "** " + description;
    }

    public String getNextCommand() {
        return nextCommand;
    }

    public abstract Optional<Voucher> createVoucherFromConsole(TextIO textIO);
}
