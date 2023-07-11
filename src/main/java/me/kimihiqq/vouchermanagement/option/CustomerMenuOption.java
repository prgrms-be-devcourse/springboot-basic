package me.kimihiqq.vouchermanagement.option;

public enum CustomerMenuOption implements ConsoleOption {
    RETURN_MAIN_MENU(0, "Return to main menu"),
    CREATE_CUSTOMER(1, "Create a new customer"),
    LIST_CUSTOMERS(2, "List all customers"),
    LIST_ALL_VOUCHERS_BY_CUSTOMER(3, "List all vouchers by customer"),
    UPDATE_CUSTOMER_STATUS(4, "Update customer status"),
    ADD_VOUCHER_TO_CUSTOMER(5, "Add a voucher to customer"),
    REMOVE_VOUCHER_FROM_CUSTOMER(6, "Remove a voucher from customer");

    private final int key;
    private final String description;

    CustomerMenuOption(int key, String description) {
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