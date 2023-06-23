package prgms.spring_week1.domain.voucher.model;

import org.springframework.stereotype.Component;
import prgms.spring_week1.domain.voucher.model.type.VoucherType;

import java.util.UUID;


public abstract class Voucher {
    protected final UUID voucherId;
    protected final VoucherType voucherType;
    protected final long discount;

    public Voucher(UUID voucherId, VoucherType voucherType, long discount) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discount = discount;
    }

    public UUID getVoucherId() {
        return voucherId;
    }
}
