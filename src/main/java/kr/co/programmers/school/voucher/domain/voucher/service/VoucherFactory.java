package kr.co.programmers.school.voucher.domain.voucher.service;

import kr.co.programmers.school.voucher.domain.voucher.domain.FixedDiscountVoucher;
import kr.co.programmers.school.voucher.domain.voucher.domain.PercentDiscountVoucher;
import kr.co.programmers.school.voucher.domain.voucher.domain.Voucher;
import kr.co.programmers.school.voucher.domain.voucher.dto.VoucherRequest;
import kr.co.programmers.school.voucher.domain.voucher.enums.VoucherType;

import java.util.UUID;

public class VoucherFactory {
    public static Voucher createVoucher(VoucherRequest voucherRequest) {
        VoucherType voucherType = voucherRequest.getVoucherType();

        if (voucherType.isFixedAmount()) {
            return new FixedDiscountVoucher(UUID.randomUUID(), voucherRequest.getAmount());
        }
        return new PercentDiscountVoucher(UUID.randomUUID(), voucherRequest.getAmount());
    }
}