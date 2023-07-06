package com.ray.junho.voucher.controller.dto;

import com.ray.junho.voucher.domain.VoucherType;
import org.apache.logging.log4j.message.Message;

public class VoucherResponse {

    private final long id;
    private final VoucherType voucherType;
    private final int discountValue;

    public VoucherResponse(long id, VoucherType voucherType, int discountValue) {
        this.id = id;
        this.voucherType = voucherType;
        this.discountValue = discountValue;
    }

    public long getId() {
        return id;
    }

    public int getDiscountValue() {
        return discountValue;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public String generateMessage() {
        return String.format("Voucher 번호: %d, 타입: %s, 할인 값: %d", id, voucherType, discountValue);
    }
}
