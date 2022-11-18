package com.program.commandLine.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private static final long MAX_VOUCHER_PERCENT = 90;

    private final UUID voucherID;
    private final int percent;
    private UUID assignedCustomerId;
    private boolean used = false;


    public PercentDiscountVoucher(UUID voucherID, int percent) {
        if (percent < 0) throw new IllegalArgumentException("! Percent should be positive");
        if (percent == 0) throw new IllegalArgumentException("! Percent should not be zero");
        if (percent > MAX_VOUCHER_PERCENT)
            throw new IllegalArgumentException("! Percent should be less than %d".formatted(MAX_VOUCHER_PERCENT));
        this.voucherID = voucherID;
        this.percent = percent;
    }

    public PercentDiscountVoucher(UUID voucherID, int percent, UUID assignedCustomerId, boolean used) {
        this.voucherID = voucherID;
        this.percent = percent;
        this.assignedCustomerId = assignedCustomerId;
        this.used = used;
    }

    @Override
    public UUID getVoucherId() {
        return voucherID;
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT_DISCOUNT;
    }

    @Override
    public int getVoucherDiscount() {
        return percent;
    }

    @Override
    public UUID getAssignedCustomerId() {
        return assignedCustomerId;
    }

    @Override
    public boolean getUsed() {
        return used;
    }

    @Override
    public int discountPrice(int beforeDiscount) {
        return (int) (beforeDiscount * (1 - (double) percent / 100));
    }

    @Override
    public void used() {
        if (used) throw new IllegalAccessError("! 이미 사용된 바우처 입니다.");
        used = true;
    }

    @Override
    public void assignCustomer(UUID customerId) {
        if (assignedCustomerId != null) throw new IllegalAccessError("! 이미 할당된 바우처 입니다.");
        assignedCustomerId = customerId;
    }

    @Override
    public void retrieved() {
        assignedCustomerId = null;
    }
}
