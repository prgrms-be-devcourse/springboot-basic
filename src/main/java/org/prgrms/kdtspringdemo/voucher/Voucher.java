package org.prgrms.kdtspringdemo.voucher;

import org.prgrms.kdtspringdemo.VoucherType;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public class Voucher {
    private final UUID voucherId;

    private String name;
    private final long discount;
    private final VoucherType voucherType;
    private final LocalDateTime createdAt;

    private LocalDateTime usedAt;

    public Voucher(UUID voucherId, String name, long discount, VoucherType voucherType, LocalDateTime createdAt) {
        validate(name);
        validate(discount);
        this.voucherId = voucherId;
        this.name = name;
        this.discount = discount;
        this.voucherType = voucherType;
        this.createdAt = createdAt;
    }
    public Voucher(UUID voucherId, String name, long discount, VoucherType voucherType, LocalDateTime createdAt, LocalDateTime usedAt) {
        validate(name);
        validate(discount);
        this.voucherId = voucherId;
        this.name = name;
        this.discount = discount;
        this.voucherType = voucherType;
        this.createdAt = createdAt;
        this.usedAt = usedAt;
    }

    private void validate(String name) {
        if (name.isBlank()) {
            throw new RuntimeException("Name should not be blank");
        }
    }

    private void validate(long discount) {
        if (discount <= 0) {
            throw new RuntimeException("Discount should not be minus value");
        }
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getDiscount() {
        return discount;
    }

    public String getName() {
        return name;
    }

    public VoucherType getType() {
        return voucherType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUsedAt() {
        return usedAt;
    }

    public String saveCSV() {
        return String.format("%s,%s,%s", name, voucherId, discount);
    }

    long discount(long beforeDiscount) {
        return 0L;
    }

    @Override
    public String toString() {
        return MessageFormat.format("[{0}] voucherId : {1}, percent : {2}", voucherType, voucherId, discount);
    }

    public void changeName(String name) {
        validate(name);
        this.name = name;
    }
}
