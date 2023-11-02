package org.prgrms.kdtspringdemo.wallet.domain.dto;

import org.prgrms.kdtspringdemo.voucher.domain.Voucher;

import java.util.List;
import java.util.UUID;

public class WalletDetailsDto {
    private UUID walletId;
    private UUID customerId;
    private List<Voucher> voucherList;

    public WalletDetailsDto(UUID walletId, UUID customerId, List<Voucher> voucherList) {
        this.walletId = walletId;
        this.customerId = customerId;
        this.voucherList = voucherList;
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

    public List<Voucher> getVoucherList() {
        return voucherList;
    }

    public void setVoucherList(List<Voucher> voucherList) {
        this.voucherList = voucherList;
    }
}
