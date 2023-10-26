package com.prgrms.vouchermanager.message;

import lombok.Getter;

@Getter
public enum ConsoleMessage {
    SELECT_PROGRAM("""
    Select the program. voucher or customer or wallet?
    If you want to exit, type exit."""),
    COMPLETE_UPDATE_CUSTOMER("Update completed."),
    GET_CUSTOMER_ID("Type the customer ID."),
    GET_VOUCHER_ID("Type the voucher ID."),
    COMPLETE_DELETE_CUSTOMER("Delete completed."),
    FINISH_PROGRAM("Exit the program."),

    SELECT_FUNCTION_VOUCHER("""
            === Voucher Program ===
            Type exit to exit the program.
            Type create to create a new voucher.
            Type list to list all vouchers."""),
    GET_VOUCHER_TYPE("""
            Type fixed if you want a fixed discount voucher.
            Type percent if you want a percent discount voucher."""),
    GET_DISCOUNT_AMOUNT("Type the discount amount."),
    GET_DISCOUNT_PERCENT("Type the discount percent."),
    COMPLETE_CREATE_VOUCHER("A voucher has been created."),

    EMPTY_LIST_EXCEPTION("There is nothing registered."),
    FILE_IO_EXCEPTION("Fail to file IO."),
    NOT_CORRECT_COMMAND("The command is incorrect."),
    NOT_CORRECT_FORM("The input format is correctly."),
    NOT_CORRECT_SCOPE("Percent range is incorrect."),
    NOT_CORRECT_ID("ID is not correct."),

    SELECT_FUNCTION_CUSTOMER("""
            === Customer Program ===
            Type exit to exit the program.
            Type create to create a new customer.
            Type list to list all customers.
            Type update to update customer.
            Type delete to delete customer.
            Type blacklist to show blacklist."""),
    GET_CUSTOMER_NAME("Type the name."),
    GET_CUSTOMER_YEAR("Type the year of birth."),
    SELECT_UPDATE_TARGET("""
            Type name if you want to update name.
            Type year if you want to update year of birth."""),

    SELECT_FUNCTION_WALLET("""
            === Wallet Program ===
            Type exit to exit the program.
            Type create to assign the voucher to customer.
            Type voucher to check which voucher a customer holds.
            Type customer to check customers holding a specific voucher.
            Type delete to delete a voucher held by a customer.""");


    private final String message;
    ConsoleMessage(String message) {
        this.message = message;
    }

}
