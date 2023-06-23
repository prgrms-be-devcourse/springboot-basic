package com.dev.bootbasic.voucher.domain;

import com.dev.bootbasic.voucher.dto.VoucherCreateRequest;
import org.springframework.stereotype.Component;

import static com.dev.bootbasic.util.UUIDProvider.createUUID;

@Component
public class VoucherFactory {

    public static Voucher createVoucher(VoucherCreateRequest request) {
        VoucherType voucherType = VoucherType.from(request.voucherType());

        return switch (voucherType) {
            case FIXED -> FixedAmountVoucher.of(createUUID(), request.discountAmount());
            case PERCENT -> PercentDiscountVoucher.of(createUUID(), request.discountAmount());
        };
    }

}
