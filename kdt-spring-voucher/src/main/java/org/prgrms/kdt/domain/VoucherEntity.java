package org.prgrms.kdt.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.prgrms.kdt.enumType.VoucherStatus;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
public class VoucherEntity {

    private UUID voucherId;
    private Enum<VoucherStatus> voucherType;
    private Long discount;
    private final LocalDateTime createdAt;

    public VoucherEntity(UUID voucherId, Enum<VoucherStatus> voucherType, Long discount, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.discount = discount;
        this.createdAt = createdAt;
    }
}
