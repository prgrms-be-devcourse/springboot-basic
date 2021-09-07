package org.prgrms.orderApp.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherDto {
    private final UUID vouchcerId;
    private final long amount ;
    private  String voucherType;
    private  LocalDateTime expiredAt, createdAt;

    public VoucherDto(UUID vouchcerId, long amount){
        this.vouchcerId = vouchcerId;
        this.amount = amount;
    }

    public VoucherDto(UUID vouchcerId, long amount, String voucherType, LocalDateTime expiredAt, LocalDateTime createdAt){
        this.vouchcerId = vouchcerId;
        this.amount = amount;
        this.voucherType = voucherType;
        this.expiredAt = expiredAt;
        this.createdAt = createdAt;
    }

    public UUID getVouchcerId() {
        return vouchcerId;
    }

    public long getAmount() {
        return amount;
    }
}
