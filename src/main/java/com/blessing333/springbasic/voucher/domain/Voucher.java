package com.blessing333.springbasic.voucher.domain;

import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class Voucher {
    private final UUID voucherId;

    protected Voucher(){
        this.voucherId = UUID.randomUUID();
    }

    public abstract long discount(long beforePrice);


}
