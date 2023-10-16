package devcourse.springbootbasic.dto;

import devcourse.springbootbasic.domain.voucher.Voucher;
import devcourse.springbootbasic.domain.voucher.VoucherType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VoucherCreateRequest {

    private final VoucherType voucherType;
    private final long discountValue;

    public Voucher toEntity() {
        return Voucher.builder()
                .voucherType(voucherType)
                .discountValue(discountValue)
                .build();
    }

    public boolean validateDiscountValue() {
        return voucherType.validateDiscountValue(discountValue);
    }
}
