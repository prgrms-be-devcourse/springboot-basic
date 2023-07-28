package org.programmers.VoucherManagement.wallet.application.dto;

public record WalletCreateRequest(
        String voucherId,
        String memberId
) {
}
