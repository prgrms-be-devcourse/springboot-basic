package org.prgrms.kdtspringdemo.wallet.domain.dto;

import org.prgrms.kdtspringdemo.voucher.domain.Voucher;

import java.util.List;
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
