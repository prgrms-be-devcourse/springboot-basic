package org.programmers.VoucherManagement.wallet.dto.request;

public record WalletCreateRequest(
        String voucherId,
        String memberId
) {
}
