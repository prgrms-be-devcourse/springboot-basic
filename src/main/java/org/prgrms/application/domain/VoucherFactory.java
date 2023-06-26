package org.prgrms.application.domain;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherFactory {

    public static Voucher create(VoucherType voucherType, Double voucherDetail) {
        switch (voucherType) {
            case FIXED:
                return new FixedAmountVoucher(UUID.randomUUID(), voucherDetail);

            case PERCENT:
                return new PercentAmountVoucher(UUID.randomUUID(), voucherDetail);

            default:
                throw new IllegalArgumentException("잘못된 입력입니다.");
        }
    }
}
