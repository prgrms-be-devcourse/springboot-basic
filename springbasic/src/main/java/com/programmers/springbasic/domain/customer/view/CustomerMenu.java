package com.programmers.springbasic.domain.customer.view;

import lombok.Getter;

@Getter
public enum CustomerMenu {
    MAIN_MENU_MESSAGE("=== Customer Program ==="),
    EXIT_MENU_MESSAGE("Type exit to exit the program."),
    CREATE_MENU_MESSAGE("Type create to create a new customer."),
    LIST_MENU_MESSAGE("Type list to list all customers."),
    READ_MENU_MESSAGE("Type read to read customer info."),
    UPDATE_MENU_MESSAGE("Type update to update customer info."),
    DELETE_MENU_MESSAGE("Type delete to delete customer."),
    SHOW_MENU_MESSAGE("Type show to show all vouchers customer have");

    String message;

    CustomerMenu(String message) {
        this.message = message;
    }
}
