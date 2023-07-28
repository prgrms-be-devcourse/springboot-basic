package org.programmers.VoucherManagement.wallet.presentation.dto;

public record WalletCreateRequestData(
        String voucherId,
        String memberId
) {
}
