package org.prgms.kdtspringweek1.voucher.entity;


import java.text.MessageFormat;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;
    private final static VoucherType voucherType = VoucherType.FIXED_AMOUNT;

    public static FixedAmountVoucher createWithAmount(long amount) {
        return new FixedAmountVoucher(amount);
    }

    public static FixedAmountVoucher createWithIdAndAmount(UUID voucherId, long amount) {
        return new FixedAmountVoucher(voucherId, amount);
    }

    private FixedAmountVoucher(long amount) {
        if (amount > 0) {
            this.voucherId = UUID.randomUUID();
            this.amount = amount;
        } else {
        }
    }

    private FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                '}';
    }

    @Override
    public void printVoucherInfo() {
        System.out.println(MessageFormat.format("Voucher Type: {0}", voucherType.getName()));
        System.out.println(MessageFormat.format("Voucher Id: {0}", voucherId));
        System.out.println(MessageFormat.format("Fixed Discount Amount: {0}", amount));
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public long getDiscountValue() {
        return amount;
    }
}
