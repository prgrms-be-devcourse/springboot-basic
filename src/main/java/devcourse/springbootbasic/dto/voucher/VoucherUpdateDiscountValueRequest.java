package devcourse.springbootbasic.dto.voucher;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VoucherUpdateDiscountValueRequest {

    private final long discountValue;
}
