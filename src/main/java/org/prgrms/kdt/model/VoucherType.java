package org.prgrms.kdt.model;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public enum VoucherType {
    FIXED_AMOUNT("fixed") {
        @Override
        public Voucher createVoucher(long value) {
            return new FixedAmountVoucher(UUID.randomUUID(), value);
        }
    },
    PERCENT("percent") {
        @Override
        public Voucher createVoucher(long value) {
            return new PercentDiscountVoucher(UUID.randomUUID(), value);
        }
    };

    private static final Map<String, VoucherType> typeByName = new HashMap<>(VoucherType.values().length);

    static {
        for (VoucherType type : VoucherType.values()) {
            typeByName.put(type.getName(), type);
        }
    }

    private final String name;

    VoucherType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract Voucher createVoucher(long value);

    public static VoucherType lookup(String name) {
        var ret = typeByName.get(name);
        if(ret != null) {
            return ret;
        }
        throw new IllegalArgumentException("No voucher type:" + name);
    }
}
