package org.prgrms.springorder.domain.voucher.api.request;

import java.util.UUID;
import lombok.Getter;
import org.prgrms.springorder.console.io.Request;
import org.prgrms.springorder.util.UUIDValidator;

@Getter
public class VoucherIdRequest implements Request {

    private final UUID voucherId;

    public VoucherIdRequest(String voucherId) {
        UUIDValidator.validateString(voucherId);

        this.voucherId = UUID.fromString(voucherId);
    }

}
