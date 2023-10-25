package devcourse.springbootbasic.dto.voucher;

import devcourse.springbootbasic.domain.voucher.Voucher;
import devcourse.springbootbasic.domain.voucher.VoucherType;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class VoucherCreateRequest {

    private final VoucherType voucherType;
    private final long discountValue;

    public Voucher toEntity() {
        return Voucher.builder()
                .id(UUID.randomUUID())
                .voucherType(voucherType)
                .discountValue(discountValue)
                .build();
    }
}
