package org.programers.vouchermanagement.voucher.dto.request;

import lombok.NoArgsConstructor;
import org.programers.vouchermanagement.voucher.domain.VoucherPolicy;
import org.programers.vouchermanagement.voucher.domain.VoucherType;

@NoArgsConstructor
public class VoucherCreationRequest {

    private VoucherPolicy policy;
    private VoucherType type;

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
