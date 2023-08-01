package org.prgrms.kdt.wallet.service.dto;

import org.prgrms.kdt.wallet.domain.QueryWallet;

import java.util.UUID;

public record JoinedWalletResponse(UUID walletId, String memberName, String voucherType, double voucherAmount) {
    public JoinedWalletResponse(QueryWallet queryWallet) {
        this(queryWallet.getWalletId(),
                queryWallet.getMember().getMemberName(),
                queryWallet.getVoucher().getVoucherType().getDescripton(),
                queryWallet.getVoucher().getDiscountPolicy().getAmount());
    }
}
