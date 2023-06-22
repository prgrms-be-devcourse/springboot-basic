package org.programers.vouchermanagement.dto;

import org.programers.vouchermanagement.domain.VoucherPolicy;

public class VoucherCreationRequest {

    private final VoucherPolicy policy;

    public VoucherCreationRequest(VoucherPolicy policy) {
        this.policy = policy;
    }

    public VoucherPolicy getPolicy() {
        return policy;
    }
}
