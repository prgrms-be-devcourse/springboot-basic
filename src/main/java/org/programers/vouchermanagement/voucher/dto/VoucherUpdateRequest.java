package org.programers.vouchermanagement.voucher.dto;

import org.programers.vouchermanagement.voucher.domain.VoucherPolicy;
import org.programers.vouchermanagement.voucher.domain.VoucherType;

import java.util.UUID;

public class VoucherUpdateRequest {

    private final UUID id;
    private final VoucherPolicy policy;
    private final VoucherType type;

    public VoucherUpdateRequest(UUID id, VoucherPolicy policy, VoucherType type) {
        this.id = id;
        this.policy = policy;
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public VoucherPolicy getPolicy() {
        return policy;
    }

    public VoucherType getType() {
        return type;
    }
}
