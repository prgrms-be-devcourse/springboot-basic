package com.prgrms.vouchermanager.domain.voucher;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class VoucherFactory {
    private VoucherFactory() {
    }

    public static Optional<Voucher> create(VoucherType voucherType, long discount) {
        if(voucherType == VoucherType.FIXED) {
            return Optional.of(new FixedAmountVoucher(discount));
        } else if(voucherType == VoucherType.PERCENT) {
            return Optional.of(new PercentAmountVoucher(discount));
        }
        return Optional.empty();
    }

    public static Optional<Voucher> update(UUID id, VoucherType voucherType, long discount) {
        if(voucherType == VoucherType.FIXED) {
            return Optional.of(new FixedAmountVoucher(id, discount));
        } else if(voucherType == VoucherType.PERCENT) {
            return Optional.of(new PercentAmountVoucher(id, discount));
        }
        return Optional.empty();
    }
}
