package com.example.demo.domain.voucher;

import com.example.demo.dto.VoucherDto;
import com.example.demo.util.VoucherType;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID id;
    private final int discountAmount;

    public FixedAmountVoucher(int discountAmount) {
        this.id = UUID.randomUUID();
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
        return new VoucherDto(UUID.fromString(this.id.toString()), discountAmount, VoucherType.FIXED_AMOUNT_VOUCHER);
    }

    @Override
    public UUID getUUID() {
        return this.id;
    }

}
