package com.prgms.management.voucher.entity;

public enum VoucherType {
    PERCENT("percent", "to create a percent discount voucher.", "Percent") {
        @Override
        public Voucher createVoucher(Integer num) {
            return new PercentDiscountVoucher(num);
        }
    },
    FIXED("fixed", "to create a fixed amount voucher.", "Amount") {
        @Override
        public Voucher createVoucher(Integer num) {
            return new FixedAmountVoucher(num);
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
                return null;
        }
    }

    public String getScript() {
        return "Type **" + command + "** " + description;
    }

    public String getNextCommand() {
        return nextCommand;
    }

    public abstract Voucher createVoucher(Integer num);
}
