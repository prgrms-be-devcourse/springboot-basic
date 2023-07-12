package kr.co.programmers.springbootbasic.wallet.dto;

import java.text.MessageFormat;

public class WalletSaveDto {
    private static final String WALLET_SAVE_FORMAT = """
            바우처 아이디 : {0}
            지갑 아이디 : {1}
                        
            """;

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

    public String formatWalletSaveDto() {
        return MessageFormat.format(WALLET_SAVE_FORMAT,
                this.voucherId,
                this.walletId);
    }
}
