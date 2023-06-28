package com.wonu606.vouchermanager.domain;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class Voucher {

    private final UUID uuid;

    public abstract double calculateDiscountedPrice(double originalPrice);
}
