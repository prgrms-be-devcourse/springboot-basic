package com.prgms.VoucherApp.domain.wallet.model.factory;

import com.prgms.VoucherApp.domain.wallet.model.strategy.*;
import com.prgms.VoucherApp.view.WalletCommand;

public final class WalletCommandStrategyFactory {

    private WalletCommandStrategyFactory() {

    }

    public static WalletCommandStrategy from(WalletCommand walletCommand) {
        WalletCommandStrategy walletCommandStrategy = switch (walletCommand) {
            case CREATE -> new WalletCreate();
            case FIND_ONE -> new WalletFindOne();
            case FIND_BY_VOUCHER_ID -> new WalletFindByVoucherId();
            case FIND_BY_CUSTOMER_ID -> new WalletFindByCustomerId();
            case DELETE_BY_VOUCHER_ID -> new WalletDeleteByVoucherId();
            case DELETE -> new WalletDelete();
            case EXIT -> new WalletExit();
        };

        return walletCommandStrategy;
    }
}
