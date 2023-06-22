package org.programers.vouchermanagement.voucher.dto;

import org.programers.vouchermanagement.voucher.domain.Voucher;
import org.programers.vouchermanagement.voucher.domain.VoucherPolicy;

import java.util.UUID;

public class VoucherResponse {

    private final UUID id;
    private final VoucherPolicy policy;

    public VoucherResponse(Voucher voucher) {
        this(voucher.getId(), voucher.getPolicy());
    }

    public VoucherResponse(UUID id, VoucherPolicy policy) {
        this.id = id;
        this.policy = policy;
    }

    public UUID getId() {
        return id;
    }

    public VoucherPolicy getPolicy() {
        return policy;
    }
}
