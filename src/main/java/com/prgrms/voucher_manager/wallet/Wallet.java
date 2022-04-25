package com.prgrms.voucher_manager.wallet;

import com.prgrms.voucher_manager.voucher.Voucher;
import lombok.Builder;

import java.util.UUID;

@Builder
public class Wallet {

    private final UUID customerId;
    private UUID voucherId;

    public Wallet(UUID customerId, UUID voucherId) {
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void changeVoucher(Voucher voucher) {
        this.voucherId = voucher.getVoucherId();
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "customerId=" + customerId +
                ", voucherId=" + voucherId +
                '}';
    }
}
