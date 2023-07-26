package com.programmers.springbasic.domain.voucher.factory;

import com.programmers.springbasic.domain.voucher.dto.request.VoucherCreateRequestDTO;
import com.programmers.springbasic.domain.voucher.entity.FixedAmountVoucher;
import com.programmers.springbasic.domain.voucher.entity.PercentDiscountVoucher;
import com.programmers.springbasic.domain.voucher.entity.Voucher;
import com.programmers.springbasic.domain.voucher.model.VoucherType;

import java.util.UUID;

public class VoucherFactory {
    public static Voucher issueVoucher(VoucherCreateRequestDTO voucherCreateRequestDTO) {
        double value = voucherCreateRequestDTO.getVoucherValue();
        UUID userId = UUID.fromString(voucherCreateRequestDTO.getCustomerId());

        switch (VoucherType.of(voucherCreateRequestDTO.getVoucherType())) {
            case FIXED_AMOUNT_VOUCHER: {
                return new FixedAmountVoucher(value, userId);
            }
            case PERCENT_DISCOUNT_VOUCHER: {
                return new PercentDiscountVoucher(value, userId);
            }
            default: {
                throw new RuntimeException("invalid voucher type");
            }
        }
    }
}
