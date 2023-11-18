package devcourse.springbootbasic.dto.voucher;

import devcourse.springbootbasic.domain.voucher.Voucher;
import devcourse.springbootbasic.domain.voucher.VoucherType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class VoucherCreateRequest {

    private final VoucherType voucherType;
    private final long discountValue;

    public Voucher toEntity(UUID voucherId) {
        return Voucher.builder()
                .id(voucherId)
                .voucherType(voucherType)
                .discountValue(discountValue)
                .build();
    }
}
