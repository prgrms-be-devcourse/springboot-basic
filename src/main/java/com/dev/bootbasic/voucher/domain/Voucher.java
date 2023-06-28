package com.dev.bootbasic.voucher.domain;

import java.util.Objects;
import java.util.UUID;

public abstract class Voucher {

    private static final String NOT_ASSIGNED_VOUCHER_ID_MESSAGE = "바우처 ID가 할당되지 않았습니다.";
    private static final String NOT_ASSIGNED_VOUCHER_TYPE_MESSAGE = "바우처의 타입이 할당되지 않았습니다.";
    private final UUID id;
    private final VoucherType voucherType;
    private final int discountAmount;

    protected Voucher(UUID id, VoucherType voucherType, int discountAmount) {
        Objects.requireNonNull(id, NOT_ASSIGNED_VOUCHER_ID_MESSAGE);
        Objects.requireNonNull(voucherType, NOT_ASSIGNED_VOUCHER_TYPE_MESSAGE);
        this.id = id;
        this.voucherType = voucherType;
        this.discountAmount = discountAmount;
    }

    public UUID getId() {
        return id;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    abstract int discount(int originPrice);

}
