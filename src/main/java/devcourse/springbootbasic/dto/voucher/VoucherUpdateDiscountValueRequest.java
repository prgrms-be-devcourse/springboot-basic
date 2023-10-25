package devcourse.springbootbasic.dto.voucher;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class VoucherUpdateDiscountValueRequest {

    private final UUID id;
    private final long discountValue;
}
