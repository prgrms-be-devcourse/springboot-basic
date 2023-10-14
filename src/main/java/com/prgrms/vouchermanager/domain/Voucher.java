package com.prgrms.vouchermanager.domain;

import lombok.Getter;

import java.util.UUID;


public interface Voucher {
    public long discount(long beforeDiscount);
    public String toString();
    public UUID getId();
}
