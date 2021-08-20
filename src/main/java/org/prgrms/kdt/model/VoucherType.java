package org.prgrms.kdt.model;


import java.util.HashMap;
import java.util.Map;

public enum VoucherType {
    FIXED_AMOUNT("fixed"),
    PERCENT("percent"),
    INVALID("invalid");

    private static final Map<String, VoucherType> nameIndex = new HashMap<>(VoucherType.values().length);

    static {
        for (VoucherType type : VoucherType.values()) {
            nameIndex.put(type.getValue(), type);
        }
    }

    private final String value;

    VoucherType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static VoucherType lookup(String value) {
        return nameIndex.getOrDefault(value, INVALID);
    }
    }
