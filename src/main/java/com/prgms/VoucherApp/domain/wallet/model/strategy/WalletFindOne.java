package com.prgms.VoucherApp.domain.wallet.model.strategy;

import com.prgms.VoucherApp.domain.voucher.model.VoucherService;
import com.prgms.VoucherApp.domain.wallet.dto.WalletResponse;
import com.prgms.VoucherApp.domain.wallet.model.WalletService;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;

import java.util.UUID;

public class WalletFindOne implements WalletCommandStrategy {

    @Override
    public void execute(Input input, Output output, WalletService walletService, VoucherService voucherService) {
        String inputWalletId = input.inputUUID();
        UUID walletId = UUID.fromString(inputWalletId);

        WalletResponse walletResponse = walletService.findOne(walletId);
        output.printWallet(walletResponse);
    }
}
