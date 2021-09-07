package org.prgrms.orderApp.voucher;

import java.util.UUID;

public class VoucherDto {
    private final UUID vouchcerId;
    private final long amount ;
    private String voucherType;

    public VoucherDto(UUID vouchcerId, long amount){
        this.vouchcerId = vouchcerId;
        this.amount = amount;
    }

    public VoucherDto(UUID vouchcerId, long amount, String voucherType){
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
