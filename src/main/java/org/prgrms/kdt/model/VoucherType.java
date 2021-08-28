package org.prgrms.kdt.model;


import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.prgrms.kdt.command.VoucherCommandOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum VoucherType {
    // TODO 람다를 이용해서 구현하기
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

    private static final Logger logger = LoggerFactory.getLogger(VoucherType.class);
    private static final Map<String, VoucherType> typeByName =
        new HashMap<>(VoucherType.values().length);

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
        if (ret != null) {
            return ret;
        }
        var msg = MessageFormat.format("No voucher type:{0}", name);
        logger.error(msg);
        throw new IllegalArgumentException(msg);
    }
}
