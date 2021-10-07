package org.prgrms.orderApp.voucher;

import java.util.UUID;


// 마땅한 이름을 찾지 못하였습니다.
public class VoucherTempEntity {
    private final UUID vouchcerId;
    private final long amount ;
    private String voucherType;

    public VoucherTempEntity(UUID vouchcerId, long amount){
        this.vouchcerId = vouchcerId;
        this.amount = amount;
    }

    public VoucherTempEntity(UUID vouchcerId, long amount, String voucherType){
        this.vouchcerId = vouchcerId;
        this.amount = amount;
        this.voucherType = voucherType;
    }

    public UUID getVouchcerId() {
        return vouchcerId;
    }

    public long getAmount() {
        return amount;
    }
}
