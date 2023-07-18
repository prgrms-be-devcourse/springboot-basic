package org.programmers.VoucherManagement.wallet.dto.request;

public class WalletCreateRequest {
    private final String voucherId;
    private final String memberId;

    public WalletCreateRequest(String voucherId, String memberId) {
        this.voucherId = voucherId;
        this.memberId = memberId;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public String getMemberId() {
        return memberId;
    }
}
