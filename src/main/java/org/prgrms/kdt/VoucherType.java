package org.prgrms.kdt;

import java.util.Optional;

public enum VoucherType {
    FIXED("fixed", "고정"),
    PERCENT("percent", "비율");

    private String type;
    private String desc;

    VoucherType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static Optional<VoucherType> matchVoucherType(String input) {
        for (VoucherType vtype : VoucherType.values()) {
            if (vtype.name().equalsIgnoreCase(input)) return Optional.of(vtype);
        }
        return Optional.empty();
    }

}
