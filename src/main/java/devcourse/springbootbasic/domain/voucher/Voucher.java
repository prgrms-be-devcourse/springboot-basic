package devcourse.springbootbasic.domain.voucher;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@RequiredArgsConstructor
public class Voucher {

    @Builder.Default
    private final UUID id = UUID.randomUUID();
    private final VoucherType voucherType;
    private final long discountValue;
}
