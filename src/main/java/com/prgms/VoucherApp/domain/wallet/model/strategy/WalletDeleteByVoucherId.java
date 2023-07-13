package com.prgms.VoucherApp.domain.wallet.model.strategy;

import com.prgms.VoucherApp.domain.voucher.model.VoucherService;
import com.prgms.VoucherApp.domain.wallet.model.WalletService;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;

import java.util.UUID;

public class WalletDeleteByVoucherId implements WalletCommandStrategy {

    @Override
    public void execute(Input input, Output output, WalletService walletService, VoucherService voucherService) {
        String inputVoucherId = input.inputUUID();
        UUID voucherId = UUID.fromString(inputVoucherId);
        voucherService.deleteById(voucherId);
    }
}
