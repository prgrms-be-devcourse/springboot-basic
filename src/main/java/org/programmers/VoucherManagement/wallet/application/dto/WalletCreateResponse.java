package org.programmers.VoucherManagement.wallet.application.dto;

public record WalletCreateResponse(
        String walletId,
        String voucherId,
        String memberId
) {
}
