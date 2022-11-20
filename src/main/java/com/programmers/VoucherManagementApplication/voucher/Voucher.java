package com.programmers.VoucherManagementApplication.voucher;

import com.programmers.VoucherManagementApplication.vo.Amount;
import com.programmers.VoucherManagementApplication.vo.VoucherType;

import java.util.UUID;

public abstract class Voucher {

    private final UUID voucherId;
    private final VoucherType voucherType;
    private final Amount amount;
    private String usageStatus = "N";

    public Voucher(UUID voucherId, VoucherType voucherType, Amount amount) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.amount = amount;
    }

    public static Voucher from(UUID id, VoucherType type, Amount amount) {
        return switch (type) {
            case FIXED_DISCOUNT -> new FixedVoucher(id, VoucherType.FIXED_DISCOUNT, amount);
            case PERCENT_DISCOUNT -> new PercentVoucher(id, VoucherType.PERCENT_DISCOUNT, amount);
        };
    }

    public void changedStatus(String status) {
        if (!(status.equals("Y") || status.equals("N"))) {
            throw new RuntimeException("status is not correct.");
        }
        this.usageStatus = status;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public Amount getAmount() {
        return amount;
    }

    public String getUsageStatus() {
        return usageStatus;
    }

    //abstract long discount();

    @Override
    public String toString() {
        return "voucherId = " + voucherId +
                ", voucherType = " + voucherType +
                ", amount = " + amount.getAmount();
    }
}
