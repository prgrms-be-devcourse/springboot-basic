package kr.co.programmers.springbootbasic.wallet.dto;

public class WalletSaveDto {
    private final String voucherId;
    private final String walletId;

    public WalletSaveDto(String voucherId, String walletId) {
        this.voucherId = voucherId;
        this.walletId = walletId;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public String getWalletId() {
        return walletId;
    }
}
