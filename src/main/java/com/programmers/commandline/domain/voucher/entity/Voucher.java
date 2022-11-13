package com.programmers.commandline.domain.voucher.entity;

import java.util.UUID;

public abstract class Voucher {
    public abstract UUID getVoucherId();
    public abstract VoucherType getType();
    public abstract Long getDiscount();
    public abstract String getAmountUnit();
}
