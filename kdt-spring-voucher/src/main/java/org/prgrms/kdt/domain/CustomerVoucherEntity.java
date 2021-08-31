package org.prgrms.kdt.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CustomerVoucherEntity {

    private final UUID customerVoucherId;
    private final UUID customerId;
    private final UUID voucherId;
    private final LocalDateTime createdAt;

    public CustomerVoucherEntity(UUID customerVoucherId, UUID customerId, UUID voucherId, LocalDateTime createdAt) {
        this.customerVoucherId = customerVoucherId;
        this.customerId = customerId;
        this.voucherId = voucherId;
        this.createdAt = createdAt;
    }
}
