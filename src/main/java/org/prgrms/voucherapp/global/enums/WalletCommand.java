package org.prgrms.voucherapp.global.enums;

import java.util.Arrays;
import java.util.Optional;

public enum WalletCommand {
    CANCEL("Get back to menu"),
    LIST("List all the customers' vouchers"),
    ASSIGN_VOUCHER("Assign voucher to the customer"),
    GET_VOUCHER("Get customer's vouchers"),
    DELETE_VOUCHER("Delete customer's voucher"),
    GET_CUSTOMER("Get voucher's customers");

    private final String description;

    WalletCommand(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Optional<WalletCommand> getMenu(int option) {
        return Arrays.stream(values())
                .filter(w -> w.ordinal()+1 == option)
                .findFirst();
    }
}
