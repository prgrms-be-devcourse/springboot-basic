package org.programmers.VoucherManagement.wallet.presentation.dto;

public record WalletCreateResponseData(
        String walletId,
        String voucherId,
        String memberId
) {
}
