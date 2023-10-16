package devcourse.springbootbasic.domain.voucher;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@EqualsAndHashCode
@RequiredArgsConstructor
public class Voucher {

    @Builder.Default
    private final UUID id = UUID.randomUUID();
    private final VoucherType voucherType;
    private final long discountValue;
}
