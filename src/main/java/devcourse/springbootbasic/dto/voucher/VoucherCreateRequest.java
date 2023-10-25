package devcourse.springbootbasic.dto.voucher;

import devcourse.springbootbasic.domain.voucher.Voucher;
import devcourse.springbootbasic.domain.voucher.VoucherType;
import devcourse.springbootbasic.util.UUIDUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VoucherCreateRequest {

    private final VoucherType voucherType;
    private final long discountValue;

    public Voucher toEntity() {
        return Voucher.builder()
                .id(UUIDUtil.generateRandomUUID())
                .voucherType(voucherType)
                .discountValue(discountValue)
                .build();
    }
}
