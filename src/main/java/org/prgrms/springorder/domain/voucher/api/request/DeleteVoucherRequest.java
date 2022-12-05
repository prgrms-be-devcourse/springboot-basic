package org.prgrms.springorder.domain.voucher.api.request;

import java.util.UUID;
import lombok.Getter;
import lombok.ToString;
import org.prgrms.springorder.util.UUIDValidator;

@Getter
@ToString
public class DeleteVoucherRequest {

    private UUID voucherId;

    private UUID customerId;

    public DeleteVoucherRequest(String voucherId, String customerId) {
        UUIDValidator.validateString(voucherId);
        UUIDValidator.validateString(customerId);
        this.voucherId = UUID.fromString(voucherId);
        this.customerId = UUID.fromString(customerId);
    }

}
