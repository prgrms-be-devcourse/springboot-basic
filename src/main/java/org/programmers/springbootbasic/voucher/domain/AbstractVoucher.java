package org.programmers.springbootbasic.voucher.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.UUID;

@Slf4j
@EqualsAndHashCode
@ToString
@Getter
public abstract class AbstractVoucher implements Voucher {

    private final UUID id;
    private final int amount;
    private final VoucherType type;
    private Long memberId;
    private final Timestamp registeredAt;

    protected AbstractVoucher(UUID id, int amount, VoucherType type) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.registeredAt = Timestamp.from(java.time.Instant.now());
    }

    public AbstractVoucher(UUID id, int amount, VoucherType type, Long memberId, Timestamp registeredAt) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.memberId = memberId;
        this.registeredAt = registeredAt;
    }
}
