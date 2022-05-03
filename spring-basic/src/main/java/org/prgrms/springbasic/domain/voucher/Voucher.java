package org.prgrms.springbasic.domain.voucher;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.time.LocalDateTime.now;
import static org.prgrms.springbasic.domain.voucher.VoucherType.FIXED;
import static org.prgrms.springbasic.domain.voucher.VoucherType.PERCENT;

@Getter
public class Voucher {
    private final UUID voucherId;
    private VoucherType voucherType;
    private long discountInfo;
    private final LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private UUID customerId;

    public Voucher(UUID voucherId, VoucherType voucherType, long discountInfo, LocalDateTime createdAt, LocalDateTime modifiedAt, UUID customerId) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discountInfo = discountInfo;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.customerId = customerId;
    }

    private Voucher(UUID voucherId, VoucherType voucherType, long discountInfo, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discountInfo = discountInfo;
        this.createdAt = createdAt;
    }

    public static Voucher fixedVoucher(UUID voucherId, long discountInfo) {
        return new Voucher(voucherId, FIXED, discountInfo, now());
    }

    public static Voucher percentVoucher(UUID voucherId, long discountInfo) {
        return new Voucher(voucherId, PERCENT, discountInfo, now());
    }

    public Voucher update(VoucherType voucherType, long discountInfo, UUID customerId) {
        this.voucherType = voucherType;
        this.discountInfo = discountInfo;
        this.modifiedAt = now();
        this.customerId = customerId;

        return this;
    }

    public Voucher assignToCustomer(UUID customerId) {
        this.customerId = customerId;

        return this;
    }

    public long discount(long beforeDiscount) {
        return this.voucherType.discount(beforeDiscount, this.discountInfo);
    }
}
