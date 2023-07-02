package com.example.demo.domain.voucher;

import com.example.demo.dto.VoucherDto;
import com.example.demo.util.VoucherType;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID id;
    private final Integer discountPercent;

    public PercentDiscountVoucher(UUID id, Integer discountPercent) {
        this.id = id;
        this.discountPercent = discountPercent;
    }

    @Override
    public double discount(int beforeAmount) {
        return beforeAmount * (discountPercent / 100.0);
    }

    @Override
    public VoucherDto convertToVoucherDto() {
        return new VoucherDto(UUID.fromString(this.id.toString()), new Integer(discountPercent), VoucherType.PERCENT_DISCOUNT_VOUCHER);
    }

    @Override
    public UUID getUUID() {
        return this.id;
    }
}
