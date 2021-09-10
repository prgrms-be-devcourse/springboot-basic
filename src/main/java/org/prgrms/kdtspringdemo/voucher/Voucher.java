package org.prgrms.kdtspringdemo.voucher;

import org.prgrms.kdtspringdemo.VoucherType;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public class Voucher {
    private final UUID voucherId;
    private final String name;
    private final long amount;
    private final VoucherType voucherType;
    private final LocalDateTime createdAt;

    public Voucher(UUID voucherId, String name, long amount, VoucherType voucherType, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.name = name;
        this.amount = amount;
        this.voucherType = voucherType;
        this.createdAt = createdAt;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getAmount() {
        return amount;
    }

    public String saveCSV() {
        return String.format("%s,%s,%s", name, voucherId, amount);
    }

    long discount(long beforeDiscount) {
        return 0L;
    }

    @Override
    public String toString() {
        return MessageFormat.format("[{0}}] voucherId : {1}, percent : {2}", voucherType, voucherId, amount);
    }
}
