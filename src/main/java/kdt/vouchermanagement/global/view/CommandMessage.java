package kdt.vouchermanagement.global.view;

public enum CommandMessage {
    MENU("""
            \nType exit to exit the program
            Type create to create a new voucher
            Type list to list all vouchers
            """),
    VOUCHER_NUM("""
            \nType Number of Voucher
            1. FixedAmountVoucher
            2. PercentDiscountVoucher
            """),
    DISCOUNT_VALUE("\nType Discount Value for Voucher\n"),
    NONE("Please valid menu!");

    private String message;

    CommandMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
