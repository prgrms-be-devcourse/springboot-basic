package org.prgrms.assignment.voucher.model;

import java.util.HashMap;
import java.util.Map;

public enum VoucherType {
    FIXED(1, "FixedAmountVoucher", "Type your discount amount"),

    PERCENT(2, "PercentDiscountVoucher", "Type your discount percent");

    VoucherType(int voucherTypeNum, String voucherTypeName, String benefitMessage) {
        this.voucherTypeNum = voucherTypeNum;
        this.voucherTypeName = voucherTypeName;
        this.benefitMessage = benefitMessage;
    }

    private final int voucherTypeNum;

    private final String voucherTypeName;

    private final String benefitMessage;

    private static final Map<Integer, VoucherType> voucherTypeNameMap = new HashMap<>();
    static {
        for(VoucherType voucherType : VoucherType.values()) {
            voucherTypeNameMap.put(voucherType.voucherTypeNum, voucherType);
        }
    }

    public String getVoucherTypeName() {
        return voucherTypeName;
    }

    public static VoucherType of(Integer voucherTypeNum) {
        return voucherTypeNameMap.get(voucherTypeNum);
    }

    public static VoucherType of(String voucherTypeName) {
        return VoucherType.valueOf(voucherTypeName.toUpperCase());
    }

    public String getBenefitMessage() {
        return benefitMessage;
    }
}
