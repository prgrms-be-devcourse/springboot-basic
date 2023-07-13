package kr.co.programmers.springbootbasic.wallet.dto;

import java.text.MessageFormat;

public record WalletSaveDto(String voucherId, String walletId) {
    private static final String WALLET_SAVE_FORMAT = """
            바우처 아이디 : {0}
            지갑 아이디 : {1}
                        
            """;

    public String formatMessage() {
        return MessageFormat.format(WALLET_SAVE_FORMAT,
                this.voucherId,
                this.walletId);
    }
}
