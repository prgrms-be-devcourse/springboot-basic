package devcourse.springbootbasic.dto.wallet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class VoucherAssignRequest {

    private final UUID customerId;
}
