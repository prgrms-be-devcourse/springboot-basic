package org.promgrammers.springbootbasic.domain.wallet.dto.response;

import java.util.List;

public record WalletListResponse(List<WalletResponse> walletList) {

    public String walletListOutPut() {
        StringBuilder sb = new StringBuilder();
        sb.append("지갑 리스트:\n");
        for (WalletResponse wallet : walletList) {
            sb.append("  ").append(wallet.walletOutput()).append("\n");
        }
        return sb.toString();
    }
}
