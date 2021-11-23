package org.prgrms.kdt.domain.wallet;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Wallet {

    private final UUID customerId;
    private final List<UUID> voucherIds;

    public Wallet(UUID customerId) {
        this.customerId = customerId;
        this.voucherIds = new ArrayList<>();
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public List<UUID> getVoucherIds() {
        return voucherIds;
    }

    public void addVoucher(UUID voucherId) {
        this.voucherIds.add(voucherId);
    }

    public void addVouchers(List<UUID> voucherIdList) {
        this.voucherIds.addAll(voucherIdList);
    }
}
