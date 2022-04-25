package org.prgrms.kdt.domain.voucher.dto;

import java.util.UUID;

public class VoucherAssignRequest {
    private String email;
    private UUID voucherId;

    public VoucherAssignRequest(String email, UUID voucherId) {
        this.email = email;
        this.voucherId = voucherId;
    }

    public String getEmail() {
        return email;
    }

    public UUID getVoucherId() {
        return voucherId;
    }
}
