package org.prgrms.kdt.voucher.domain;

public enum VoucherType {
    FIXED("fixed"),
    PERCENT("percent"),
    UNDEFINED("");

    private final String typeStr;

    VoucherType(String typeStr) {
        this.typeStr = typeStr;
    }

    static public VoucherType convert(String inputType){
        for(VoucherType voucherType : values()){
            if(voucherType.name().equals(inputType) || voucherType.typeStr.equals(inputType)){
                return voucherType;
            }
        }
        return UNDEFINED;
    }
}

