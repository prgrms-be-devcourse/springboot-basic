package org.prgrms.kdt.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.prgrms.kdt.enumType.VoucherStatus;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Builder
@Getter
@Setter
public class VoucherEntity {

    private UUID voucherId;
    private Optional<String> voucherType;
    private Optional<Long> discount;
    private final LocalDateTime createdAt;

    public VoucherEntity(UUID voucherId, String voucherType, long discount, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.voucherType = Optional.ofNullable(voucherType);
        this.discount = Optional.of(discount);
        this.createdAt = createdAt;
    }
}
