package org.prgrms.kdtspringdemo.voucher;

import org.prgrms.kdtspringdemo.commandline_application.CommandType;

public enum VoucherType {
    FIXED,PERCENT,ERROR;
    public static VoucherType getTypeByName(String string) {
        try {
            return VoucherType.valueOf(string.toUpperCase());
        } catch (Exception e) {
            return ERROR;
        }
    }
}
