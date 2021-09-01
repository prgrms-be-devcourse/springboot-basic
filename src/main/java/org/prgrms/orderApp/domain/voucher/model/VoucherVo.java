package org.prgrms.orderApp.domain.voucher.model;

import java.util.UUID;

public class VoucherVo {

    private final UUID vouchcerId;
    private final long amount ;

    public VoucherVo(UUID vouchcerId, long amount){
        this.vouchcerId = vouchcerId;
        this.amount = amount;
    }

    public UUID getVouchcerId() {
        return vouchcerId;
    }

    public long getAmount() {
        return amount;
    }

}
