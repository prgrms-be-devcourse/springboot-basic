package kr.co.springbootweeklymission.voucher.domain.creators;

import kr.co.springbootweeklymission.voucher.domain.entity.Voucher;
import kr.co.springbootweeklymission.voucher.domain.model.VoucherPolicy;

import java.util.UUID;

public class VoucherCreators {
    private VoucherCreators() {
    }

    public static Voucher createFixedDiscount() {
        return Voucher.builder()
                .voucherId(UUID.randomUUID())
                .amount(10)
                .voucherPolicy(VoucherPolicy.FIXED_DISCOUNT)
                .build();
    }

    public static Voucher createPercentDiscount() {
        return Voucher.builder()
                .voucherId(UUID.randomUUID())
                .amount(10)
                .voucherPolicy(VoucherPolicy.PERCENT_DISCOUNT)
                .build();
    }

    public static Voucher updateVoucher(UUID voucherId,
                                        int amount,
                                        VoucherPolicy voucherPolicy) {
        return Voucher.builder()
                .voucherId(voucherId)
                .amount(amount)
                .voucherPolicy(voucherPolicy)
                .build();
    }
}
