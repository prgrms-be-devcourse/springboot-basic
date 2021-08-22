package org.prgrms.kdtspringw1d1;

public enum VoucherType {
    FIXED("fixed"),
    PERCENT("percent");

    final private String type;

    VoucherType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
