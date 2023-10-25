package devcourse.springbootbasic.dto.voucher;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class VoucherAssignRequest {

    private final UUID voucherId;
    private final UUID customerId;
}
