package com.prgrms.vouchermanagement.core.wallet.dto;

public class WalletDto {

    private final String id;
    private final String customerId;
    private final String voucherId;

    public WalletDto(String id, String customerId, String voucherId) {
        this.id = id;
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getVoucherId() {
        return voucherId;
    }
}
