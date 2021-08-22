package org.prgrms.kdt.Voucher;

import java.util.UUID;

public class VoucherRequest {


    public  UUID voucherId;
    public  long discount;


    public VoucherRequest(UUID voucherId, long discount) {
        this.voucherId = voucherId;
        this.discount = discount;
    }

    public long getDiscount() {
        return discount;
    }
}
