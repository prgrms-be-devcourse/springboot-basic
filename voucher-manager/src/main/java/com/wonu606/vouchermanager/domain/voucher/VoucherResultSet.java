package com.wonu606.vouchermanager.domain.voucher;

import java.util.UUID;

public class VoucherResultSet {

    private UUID uuid;
    private String simpleName;
    private double discountValue;

    public VoucherResultSet(UUID uuid, String simpleName, double discountValue) {
        this.uuid = uuid;
        this.simpleName = simpleName;
        this.discountValue = discountValue;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public double getDiscountValue() {
        return discountValue;
    }
}
