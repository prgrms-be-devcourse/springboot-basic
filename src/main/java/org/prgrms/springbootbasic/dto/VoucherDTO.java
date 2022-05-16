package org.prgrms.springbootbasic.dto;

import java.io.Serializable;
import java.util.UUID;
import org.prgrms.springbootbasic.controller.VoucherType;

public class VoucherDTO implements Serializable {

    private final UUID voucherId;
    private final VoucherType voucherType;
    private final int amount;
    private final int percent;
    private final UUID customerId;

    public VoucherDTO(UUID voucherId, VoucherType voucherType, int amount, int percent,
        UUID customerId) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.amount = amount;
        this.percent = percent;
        this.customerId = customerId;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public int getAmount() {
        return amount;
    }

    public int getPercent() {
        return percent;
    }

    public UUID getCustomerId() {
        return customerId;
    }
}
