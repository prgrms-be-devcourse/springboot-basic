package org.prgrms.springorder.domain.voucher.api;

import java.util.UUID;
import org.prgrms.springorder.console.io.Request;
import org.prgrms.springorder.util.UUIDValidator;

public class VoucherIdRequest implements Request {

    private final UUID voucherId;

    public VoucherIdRequest(String voucherId) {
        UUIDValidator.validateString(voucherId);

        this.voucherId = UUID.fromString(voucherId);
    }

    public UUID getVoucherId() {
        return voucherId;
    }

}
