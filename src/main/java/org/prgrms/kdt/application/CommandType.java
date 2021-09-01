package org.prgrms.kdt.application;

public enum CommandType {
    CREATE,
    LIST,
    ALLOCATE_CUSTOMER,
    LIST_CUSTOMER_VOUCHERS,
    DELETE_CUSTOMER_VOUCHER,
    GET_VOUCHER_OWNER,
    EXIT;

    public static boolean has(String command) {
        for(CommandType type : CommandType.values()) {
            if (type.name().equals(command.toUpperCase()))
                return true;
        }
        return false;
    }
}
