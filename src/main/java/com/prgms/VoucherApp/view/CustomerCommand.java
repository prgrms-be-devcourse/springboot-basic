package com.prgms.VoucherApp.view;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum CustomerCommand {
    CREATE(1),
    FIND_ALL(2),
    FIND_ONE(3),
    FIND_BY_STATUS(4),
    FIND_BLACKLIST(5),
    UPDATE(6),
    DELETE(7),
    EXIT(8);

    private final int customerCommandNumber;

    CustomerCommand(int customerCommandNumber) {
        this.customerCommandNumber = customerCommandNumber;
    }

    private static final Map<Integer, CustomerCommand> CUSTOMER_COMMAND_MAP = Collections.unmodifiableMap(Arrays.stream(values())
        .collect(Collectors.toMap(CustomerCommand::getCustomerCommandNumber, Function.identity())));

    public static CustomerCommand findByCustomerTypeNumber(int customerCommandNumber) {
        return CUSTOMER_COMMAND_MAP.get(customerCommandNumber);
    }

    public static boolean containsCustomerCommand(int commandNumber) {
        return CUSTOMER_COMMAND_MAP.containsKey(commandNumber);
    }

    public static List<Integer> getAllCustomerCommand() {
        return CUSTOMER_COMMAND_MAP.keySet()
            .stream()
            .toList();
    }

    public String getCustomerCommandName() {
        return name().toLowerCase();
    }

    public int getCustomerCommandNumber() {
        return customerCommandNumber;
    }

    public boolean isCreate() {
        return this == CREATE;
    }

    public boolean isFindAll() {
        return this == FIND_ALL;
    }

    public boolean isFindOne() {
        return this == FIND_ONE;
    }

    public boolean isFindByStatus() {
        return this == FIND_BY_STATUS;
    }

    public boolean isFindBlackList() {
        return this == FIND_BLACKLIST;
    }

    public boolean isUpdate() {
        return this == UPDATE;
    }

    public boolean isDelete() {
        return this == DELETE;
    }

    public boolean isExit() {
        return this == EXIT;
    }
}
