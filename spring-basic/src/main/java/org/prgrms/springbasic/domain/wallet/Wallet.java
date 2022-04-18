package org.prgrms.springbasic.domain.wallet;

import lombok.Builder;
import lombok.Getter;
import org.prgrms.springbasic.domain.customer.CustomerType;
import org.prgrms.springbasic.domain.voucher.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Wallet {
    private final UUID customerId;
    private final CustomerType customerType;
    private final String name;
    private final UUID voucherId;
    private final VoucherType voucherType;
    private final long discountInfo;
    private final LocalDateTime createdAt;

    @Builder
    public Wallet(UUID customerId, CustomerType customerType, String name, UUID voucherId, VoucherType voucherType, long discountInfo, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.customerType = customerType;
        this.name = name;
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discountInfo = discountInfo;
        this.createdAt = createdAt;
    }
}
