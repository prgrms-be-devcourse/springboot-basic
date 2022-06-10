package com.programmers.part1.domain;

import lombok.Getter;

import java.util.UUID;

@Getter
public class VoucherWallet {

    private final UUID voucherWalletId;
    private final UUID customerId;
    private final UUID voucherId;

    public VoucherWallet(UUID voucherWalletId, UUID customerId, UUID voucherId) {
        this.voucherWalletId = voucherWalletId;
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

    @Override
    public String toString() {
        return String.format("현재 바우처 %-20s는 고객 %-20s의 소유입니다.", voucherId, customerId);
    }
}
