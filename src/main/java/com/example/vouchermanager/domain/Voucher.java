package com.example.vouchermanager.domain;

import java.util.UUID;

public interface Voucher {
    public long discount(long beforeDiscount);
    public String toString();
}
