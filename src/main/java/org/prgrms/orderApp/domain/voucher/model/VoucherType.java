package org.prgrms.orderApp.domain.voucher.model;


import org.json.simple.JSONObject;

import java.util.UUID;

public enum VoucherType {
    FIXEDAMOUNT(1, "FIXED", 100000000, "org.prgrms.orderApp.domain.voucher.model.FixedAmountVoucher"),
    PERCENTAMOUNT(2, "PERCENT", 100, "org.prgrms.orderApp.domain.voucher.model.PercentDiscountVoucher");


    private int menuNumber;
    private long limit;
    private String menuName, entityClassName;

    VoucherType(int menuNumber, String menuName, long limit, String entityClassName) {
        this.menuNumber = menuNumber;
        this.menuName = menuName;
        this.limit = limit;
        this.entityClassName = entityClassName;
    }

    public String getMenuName() {
        return menuName;
    }

    public String getEntityClassName() {
        return entityClassName;
    }

    public long getLimit() {
        return limit;
    }

    public static String getMenuName(int menuNumber) {
        if (menuNumber == VoucherType.FIXEDAMOUNT.menuNumber) {
            return VoucherType.FIXEDAMOUNT.menuName;
        } else if (menuNumber == VoucherType.PERCENTAMOUNT.menuNumber) {
            return VoucherType.PERCENTAMOUNT.menuName;
        } else {
            return "";
        }
    }

    public static Voucher getEntityByEntityClassName(JSONObject oneDataFromMongu) {

        if (VoucherType.PERCENTAMOUNT.entityClassName.equals(oneDataFromMongu.get("voucherType"))) {
            return new PercentDiscountVoucher(UUID.fromString((String) oneDataFromMongu.get("voucherId")), (Long) oneDataFromMongu.get("voucherAmount"));
        }
        if (VoucherType.FIXEDAMOUNT.entityClassName.equals(oneDataFromMongu.get("voucherType"))) {
            return new FixedAmountVoucher(UUID.fromString((String) oneDataFromMongu.get("voucherId")), (Long) oneDataFromMongu.get("voucherAmount"));
        }
        return null;
    }

}
