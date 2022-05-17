package org.programmers.kdtspring.dto;

import org.programmers.kdtspring.entity.voucher.Voucher;

import java.util.UUID;

public class ResponseVoucher extends Voucher {

    private final String voucherType;
    private final int discount;


    public ResponseVoucher(UUID voucherId, String voucherType, int discount, UUID customerId) {
        super(voucherId, customerId);
        this.voucherType = voucherType;
        this.discount = discount;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public int getDiscount() {
        return discount;
    }

}