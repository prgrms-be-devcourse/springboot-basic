package org.prgrms.kdtspringdemo.voucher;

import org.prgrms.kdtspringdemo.commandline_application.CommandType;

public enum VoucherType {
    FIXED, PERCENT;
    public static VoucherType getTypeByName(String string) throws IllegalArgumentException {
        return VoucherType.valueOf(string.toUpperCase());
    }
}
