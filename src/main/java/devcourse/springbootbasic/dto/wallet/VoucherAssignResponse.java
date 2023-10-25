package devcourse.springbootbasic.dto.wallet;

import lombok.Getter;

import java.util.UUID;

@Getter
public class VoucherAssignResponse {

    private final UUID voucherId;
    private final UUID customerId;

    public VoucherAssignResponse(UUID voucherId, UUID customerId) {
        this.voucherId = voucherId;
        this.customerId = customerId;
    }
}
