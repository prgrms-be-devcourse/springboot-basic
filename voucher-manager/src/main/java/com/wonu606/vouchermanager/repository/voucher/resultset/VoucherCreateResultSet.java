package com.wonu606.vouchermanager.repository.voucher.resultset;

import java.util.UUID;

public class VoucherCreateResultSet {

    private final UUID uuid;
    private final String simpleName;
    private final Double discountValue;

    public VoucherCreateResultSet(UUID uuid, String simpleName, Double discountValue) {
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

    public Double getDiscountValue() {
        return discountValue;
    }
}
