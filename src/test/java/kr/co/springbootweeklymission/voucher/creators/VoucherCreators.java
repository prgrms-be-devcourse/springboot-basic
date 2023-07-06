package kr.co.springbootweeklymission.voucher.creators;

import kr.co.springbootweeklymission.voucher.domain.entity.Voucher;
import kr.co.springbootweeklymission.voucher.domain.model.VoucherPolicy;
import kr.co.springbootweeklymission.voucher.presentation.dto.request.VoucherReqDTO;

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

    public static VoucherReqDTO.UPDATE updateVoucherInformation(int amount,
                                                                VoucherPolicy voucherPolicy) {
        return VoucherReqDTO.UPDATE.builder()
                .amount(amount)
                .voucherPolicy(voucherPolicy)
                .build();
    }
}
