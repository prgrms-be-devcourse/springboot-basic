package me.programmers.springboot.basic.springbootbasic.voucher.model;

import me.programmers.springboot.basic.springbootbasic.voucher.dto.VoucherUpdateRequestDto;

import java.io.Serializable;
import java.util.UUID;

public abstract class Voucher implements Serializable {

    private UUID voucherId;

    protected Voucher(UUID voucherId) {
        this.voucherId = voucherId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public abstract long discount(long beforeDiscount);

    public abstract void update(VoucherUpdateRequestDto requestDto);
}
