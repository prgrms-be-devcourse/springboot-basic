package org.programmers.VoucherManagement.voucher.domain;

import org.programmers.VoucherManagement.voucher.dto.CreateVoucherRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherFactory {
    public Voucher createVoucher(CreateVoucherRequest createVoucherRequest){
        DiscountType discountType = createVoucherRequest.getDiscountType();

        Voucher voucher = switch (discountType) {
            case FIXED ->
                    new FixedAmountVoucher(UUID.randomUUID(), discountType, new DiscountValue(createVoucherRequest.getDiscountValue()));
            case PERCENT ->
                    new PercentAmountVoucher(UUID.randomUUID(), discountType, new DiscountValue(createVoucherRequest.getDiscountValue()));
        };

        return voucher;
    }
}
