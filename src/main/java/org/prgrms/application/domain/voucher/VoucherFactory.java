package org.prgrms.application.domain.voucher;

import org.springframework.stereotype.Component;

import java.util.Random;

import static java.lang.Math.abs;
import static org.prgrms.application.domain.voucher.VoucherType.FIXED;
import static org.prgrms.application.domain.voucher.VoucherType.PERCENT;

@Component
public class VoucherFactory {

    public Voucher create(VoucherType voucherType, double voucherDetail) {
        long randomId = abs(new Random().nextLong());

        switch (voucherType) {
            case FIXED:
                return new FixedAmountVoucher(randomId, FIXED, voucherDetail);

            case PERCENT:
                return new PercentAmountVoucher(randomId, PERCENT, voucherDetail);

            default:
                throw new IllegalArgumentException("잘못된 입력입니다.");
        }
    }
}
