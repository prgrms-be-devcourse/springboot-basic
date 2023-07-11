package com.prgms.VoucherApp.view;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum ManagementType {
    CUSTOMER(1), VOUCHER(2), WALLET(3), EXIT(4);

    private final int typeNumber;
    private static final Map<Integer, ManagementType> MANAGEMENT_TYPE_MAP = Arrays.stream(values())
        .collect(Collectors.toMap(ManagementType::getTypeNumber, Function.identity()));

    ManagementType(int typeNumber) {
        this.typeNumber = typeNumber;
    }

    public static boolean containsManagementType(Integer commandNumber) {
        return MANAGEMENT_TYPE_MAP.containsKey(commandNumber);
    }

    public static ManagementType findByType(int typeNumber) {
        return MANAGEMENT_TYPE_MAP.get(typeNumber);
    }

    public String getTypeName() {
        return name().toLowerCase();
    }

    public int getTypeNumber() {
        return typeNumber;
    }

}
