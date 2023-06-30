package com.example.demo.domain.voucher;

import com.example.demo.dto.VoucherDto;
import com.example.demo.util.VoucherType;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID id;
    private final Integer discountAmount;

    public FixedAmountVoucher(UUID id, Integer discountAmount) {
        this.id = id;
        this.discountAmount = discountAmount;
    }

    @Override
    public double disCount(int beforeAmount) {
        double result = beforeAmount - discountAmount;
        if (isNegativeAmount(result)) {
            return 0;
        }
        return result;
    }

    private boolean isNegativeAmount(double result) {
        return result < 0;
    }

    @Override
    public VoucherDto convertToVoucherDto() {
        return new VoucherDto(UUID.fromString(this.id.toString()), new Integer(discountAmount), VoucherType.FIXED_AMOUNT_VOUCHER);
    }

}
