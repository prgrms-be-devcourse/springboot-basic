package org.programers.vouchermanagement.voucher.dto;

import org.programers.vouchermanagement.voucher.domain.VoucherPolicy;
import org.programers.vouchermanagement.voucher.domain.VoucherType;

public class VoucherCreationRequest {

    private final VoucherPolicy policy;
    private final VoucherType type;

    public VoucherCreationRequest(VoucherPolicy policy, VoucherType type) {
        this.policy = policy;
        this.type = type;
    }

    public VoucherPolicy getPolicy() {
        return policy;
    }

    public VoucherType getType() {
        return type;
    }
}
