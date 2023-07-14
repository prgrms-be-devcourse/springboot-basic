package org.prgrms.kdt.voucher.dto;

import org.prgrms.kdt.voucher.domain.Voucher;

import java.util.UUID;

public record VoucherResponse(UUID voucherId, String voucherType, double amount) {

    public VoucherResponse(Voucher voucher) {
        this(voucher.getVoucherId(), voucher.getVoucherType().getDescripton(), voucher.getDiscountPolicy().getAmount());
    }
}
