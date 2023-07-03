package me.kimihiqq.vouchermanagement.option;

public enum VoucherMenuOption implements ConsoleOption {
    RETURN_MAIN_MENU(0, "Return to main menu"),
    CREATE_VOUCHER(1, "Create a new voucher"),
    LIST_VOUCHERS(2, "List all vouchers");

    private final int key;
    private final String description;

    VoucherMenuOption(int key, String description) {
        this.key = key;
        this.description = description;
    }

    @Override
    public int getKey() {
        return key;
    }

    @Override
    public String getDescription() {
        return description;
    }
}