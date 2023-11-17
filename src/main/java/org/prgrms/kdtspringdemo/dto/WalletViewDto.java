package org.prgrms.kdtspringdemo.dto;

import org.prgrms.kdtspringdemo.dto.VoucherViewDto;
import org.prgrms.kdtspringdemo.wallet.domain.Wallet;

import java.util.List;
import java.util.UUID;

public class WalletViewDto {
    private UUID walletId;
    private UUID customerId;
    private List<VoucherViewDto> voucherList;

    public WalletViewDto(UUID walletId, UUID customerId, List<VoucherViewDto> voucherList) {
        this.walletId = walletId;
        this.customerId = customerId;
        this.voucherList = voucherList;
    }

    public WalletViewDto(Wallet wallet) {
        this.walletId = wallet.getWalletId();
        this.customerId = wallet.getCustomerId();
    }

    public UUID getWalletId() {
        return walletId;
    }

    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public List<VoucherViewDto> getVoucherList() {
        return voucherList;
    }

    public void setVoucherList(List<VoucherViewDto> voucherList) {
        this.voucherList = voucherList;
    }
}
