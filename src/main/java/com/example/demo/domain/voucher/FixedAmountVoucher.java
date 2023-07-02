package com.example.demo.domain.voucher;

import com.example.demo.dto.VoucherDto;
import com.example.demo.util.VoucherType;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID id;
    private final double discountAmount;

    public FixedAmountVoucher(double discountAmount) {
        this.id = UUID.randomUUID();
        this.discountAmount = discountAmount;
    }

    @Override
    public double discount(double beforeAmount) {
        double result = beforeAmount - discountAmount;
        if (result < 0) {
            return 0;
        }
        return result;
    }

    @Override
    public VoucherDto convertToVoucherDto() {
        return new VoucherDto(UUID.fromString(this.id.toString()), discountAmount, VoucherType.FIXED_AMOUNT_VOUCHER);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

}
