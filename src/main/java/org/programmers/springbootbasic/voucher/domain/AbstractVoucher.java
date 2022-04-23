package org.programmers.springbootbasic.voucher.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@EqualsAndHashCode
@ToString
public abstract class AbstractVoucher implements Voucher {

    private final UUID id;
    private final int amount;
    private final VoucherType type;
    private Long memberId;

    protected AbstractVoucher(UUID id, int amount, VoucherType type) {
        this.id = id;
        this.amount = amount;
        this.type = type;
    }

    protected AbstractVoucher(UUID id, int amount, VoucherType type, Long memberId) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.memberId = memberId;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public VoucherType getType() {
        return type;
    }

    @Override
    public Long getMemberId() {
        return memberId;
    }
}
