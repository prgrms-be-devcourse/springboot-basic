package com.programmers.voucher.domain.voucher;

import com.programmers.voucher.stream.voucher.VoucherStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherFactory {

    private final VoucherStream voucherStream;
    private static final Logger log = LoggerFactory.getLogger(VoucherFactory.class.getSimpleName());

    public VoucherFactory(VoucherStream voucherStream) {
        this.voucherStream = voucherStream;
    }

    public Voucher createVoucher(VoucherEnum voucherEnum, Integer inputNumber) {
        Voucher voucher = (VoucherEnum.FIXED == voucherEnum) ? createFixedVoucher(inputNumber) : createPercentVoucher(inputNumber);;
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
            log.info("rate exceeded, [user input] : {}", rate);
            throw new IllegalArgumentException("rate cannot exceed 100 percent. Do you want FixedAmountVoucher?");
        }
    }
}
