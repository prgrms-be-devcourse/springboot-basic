package org.prgrms.kdt.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.prgrms.kdt.enumType.VoucherStatus;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@Builder
public class VoucherEntity {

    private UUID voucherId;
    private Optional<String> voucherType;
    private Optional<Long> discount;
    private final LocalDateTime createdAt;

    public VoucherEntity(UUID voucherId, Optional<String> voucherType, Optional<Long> discount, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discount = discount;
        this.createdAt = createdAt;
    }
}
