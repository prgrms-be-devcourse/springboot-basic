package devcourse.springbootbasic.dto.wallet;

import lombok.Getter;

import java.util.UUID;

@Getter
public class VoucherAssignResponse {

    private final String voucherId;
    private final String customerId;

    public VoucherAssignResponse(UUID voucherId, UUID customerId) {
        this.voucherId = voucherId.toString();
        this.customerId = customerId.toString();
    }
}
