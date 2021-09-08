package org.prgms.order.voucher.entity;

public enum VoucherIndexType {
    FIXED("Fixed"),
    PERCENT("Percent");

    private final String type;

    VoucherIndexType(String type) {
        this.type = type;
    }

    public static VoucherIndexType indexToType(int type) {
        try {
            if(type==0){
                return VoucherIndexType.FIXED;
            }else if(type==1){
                return VoucherIndexType.PERCENT;
            }
        } catch (Exception e) {
            throw new NullPointerException("Not Exist Type. : "+e);
        }
        return null;
    }
}
