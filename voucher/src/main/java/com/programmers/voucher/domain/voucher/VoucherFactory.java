package com.programmers.voucher.domain.voucher;

import com.programmers.voucher.domain.enums.VoucherType;
import com.programmers.voucher.stream.voucher.VoucherStream;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherFactory {

    private final VoucherStream voucherStream;

    public VoucherFactory(VoucherStream voucherStream) {
        this.voucherStream = voucherStream;
    }

    public Voucher createVoucher(VoucherType voucherType, Integer inputNumber) {
        Voucher voucher = (VoucherType.FIXED == voucherType) ? createFixedVoucher(inputNumber) : createPercentVoucher(inputNumber);;
        voucherStream.save(voucher);
        return voucher;
    }

    private Voucher createFixedVoucher(Integer inputNumber) {
        return new FixedAmountVoucher(UUID.randomUUID().toString().substring(0, 7), inputNumber);
    }

    private PercentDiscountVoucher createPercentVoucher(Integer inputNumber) {
        validateRate(inputNumber);
        return new PercentDiscountVoucher(UUID.randomUUID().toString().substring(0, 7), inputNumber);
    }

    private static void validateRate(Integer rate) {
        if (rate >= 100) {
            throw new IllegalArgumentException("rate cannot exceed 100 percent. Do you want FixedAmountVoucher?");
        }
    }
}
