package com.prgms.VoucherApp.view;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum VoucherCommand {
    CREATE(1),
    FIND_ALL(2),
    FIND_ONE(3),
    FIND_BY_VOUCHER_TYPE(4),
    UPDATE(5),
    DELETE(6),
    EXIT(7);

    private final int voucherCommandNumber;

    private static final Map<Integer, VoucherCommand> VOUCHER_COMMAND_MAP = Collections.unmodifiableMap(Arrays.stream(values())
        .collect(Collectors.toMap(VoucherCommand::getVoucherCommandNumber, Function.identity())));

    VoucherCommand(int voucherCommandNumber) {
        this.voucherCommandNumber = voucherCommandNumber;
    }

    public static VoucherCommand findByVoucherCommandNumber(int voucherCommandNumber) {
        return VOUCHER_COMMAND_MAP.get(voucherCommandNumber);
    }

    public static boolean containsVoucherCommand(int commandNumber) {
        return VOUCHER_COMMAND_MAP.containsKey(commandNumber);
    }

    public static List<Integer> getAllVoucherCommand() {
        return VOUCHER_COMMAND_MAP.keySet()
            .stream()
            .toList();
    }

    public String getVoucherCommandName() {
        return name().toLowerCase();
    }

    public int getVoucherCommandNumber() {
        return voucherCommandNumber;
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

    public boolean isFindByVoucherType() {
        return this == FIND_BY_VOUCHER_TYPE;
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
