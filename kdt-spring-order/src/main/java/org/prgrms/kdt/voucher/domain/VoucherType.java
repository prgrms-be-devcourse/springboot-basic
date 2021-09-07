package org.prgrms.kdt.voucher.domain;

public enum VoucherType {
    FIXED("FIXED"),
    PERCENT("PERCENT");

    private final String typeStr;

    VoucherType(String typeStr) {
        this.typeStr = typeStr;
    }

    public static VoucherType convert(String inputType){
        for(VoucherType voucherType : values()){
            if(voucherType.name().equals(inputType)){
                return voucherType;
            }
        }
        throw new RuntimeException(inputType + " is not VoucherType");
    }
}

