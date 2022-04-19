package org.prgms.voucherProgram.dto;

import java.util.UUID;

public class WalletRequest {
    private String customerEmail;
    private UUID voucherId;

    public WalletRequest(String customerEmail, UUID voucherId) {
        this.customerEmail = customerEmail;
        this.voucherId = voucherId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(UUID voucherId) {
        this.voucherId = voucherId;
    }
}
