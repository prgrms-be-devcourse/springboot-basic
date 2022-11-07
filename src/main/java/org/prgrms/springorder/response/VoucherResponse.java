package org.prgrms.springorder.response;

import java.util.UUID;
import org.prgrms.springorder.domain.VoucherType;

public class VoucherResponse {

    private final UUID voucherId;

    private final long amount;

    private final VoucherType voucherType;

    public VoucherResponse(UUID voucherId, long amount, VoucherType voucherType) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.voucherType = voucherType;
    }

}
