package org.programmers.weekly.mission.domain.voucher.model;

import java.util.UUID;

public class VoucherDto implements Voucher {
    private final UUID voucherId;
    private final String type;
    private long discount;

    public VoucherDto(UUID voucherId, String type, long discount) {
        this.voucherId = voucherId;
        this.type = type;
        this.discount = discount;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public String getType() {
        return type;
    }

    public long getDiscount() {
        return discount;
    }

    @Override
    public long discount(long beforeDiscount) {
        return 0;
    }

    @Override
    public String toString() {
        return "VoucherDto{" +
                "voucherId=" + voucherId +
                ", type='" + type + '\'' +
                ", discount=" + discount +
                '}';
    }
}
