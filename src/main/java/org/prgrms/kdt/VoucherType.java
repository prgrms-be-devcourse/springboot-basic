package org.prgrms.kdt;

import org.prgrms.kdt.Exception.VoucherTypeNotMatchException;

public enum VoucherType{
    ALL("전체 바우처 목록 조회"),
    FIXED("fixed amount voucher 목록 조회"),
    PERCENT("percent amount voucher 목록 조회");

    private String type;

    VoucherType(String type) {
        this.type = type;
    }

    public static VoucherType matchVoucherType(String input) throws VoucherTypeNotMatchException {
        for (VoucherType commandType : VoucherType.values()) {
            if (commandType.toString().equalsIgnoreCase(input)) return commandType;
        }
        throw new VoucherTypeNotMatchException();
    }

    public static VoucherType toVoucherType(String input) {
        VoucherType voucher = null;
        for (VoucherType commandType : VoucherType.values()) {
            if (commandType.toString().equalsIgnoreCase(input)) {
                voucher = commandType;
                return voucher;
            }
        }
        return voucher;
    }

}
