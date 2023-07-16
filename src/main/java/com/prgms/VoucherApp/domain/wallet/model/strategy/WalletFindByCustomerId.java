package com.prgms.VoucherApp.domain.wallet.model.strategy;

import com.prgms.VoucherApp.domain.voucher.model.VoucherService;
import com.prgms.VoucherApp.domain.wallet.dto.WalletsResponse;
import com.prgms.VoucherApp.domain.wallet.model.WalletService;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;

import java.util.UUID;

public class WalletFindByCustomerId implements WalletCommandStrategy {

    @Override
    public void execute(Input input, Output output, WalletService walletService, VoucherService voucherService) {
        String inputCustomerId = input.inputUUID();
        UUID customerId = UUID.fromString(inputCustomerId);
        WalletsResponse walletsResponse = walletService.findAllByCustomerId(customerId);
        output.printWallets(walletsResponse);
    }
}
