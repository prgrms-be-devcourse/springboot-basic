package org.programmers.VoucherManagement.wallet.dto;

public class CreateWalletRequest {
    private final String voucherId;
    private final String memberId;

    public CreateWalletRequest(String voucherId, String memberId) {
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
