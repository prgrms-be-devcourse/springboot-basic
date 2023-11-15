package org.prgrms.kdtspringdemo.dto;

import java.util.UUID;

public class AddVoucherToWalletDto {

    private UUID walletId;
    private UUID selectedVoucherId;

    public AddVoucherToWalletDto() {
    }

    public AddVoucherToWalletDto(UUID walletId, UUID selectedVoucherId) {
        this.walletId = walletId;
        this.selectedVoucherId = selectedVoucherId;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public UUID getSelectedVoucherId() {
        return selectedVoucherId;
    }

    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }

    public void setSelectedVoucherId(UUID selectedVoucherId) {
        this.selectedVoucherId = selectedVoucherId;
    }
}
