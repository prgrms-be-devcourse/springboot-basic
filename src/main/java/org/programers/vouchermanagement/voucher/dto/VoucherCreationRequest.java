package org.programers.vouchermanagement.voucher.dto;

import org.programers.vouchermanagement.voucher.domain.VoucherPolicy;

public class VoucherCreationRequest {

    private final VoucherPolicy policy;

    public VoucherCreationRequest(VoucherPolicy policy) {
        this.policy = policy;
    }

    public VoucherPolicy getPolicy() {
        return policy;
    }
}
