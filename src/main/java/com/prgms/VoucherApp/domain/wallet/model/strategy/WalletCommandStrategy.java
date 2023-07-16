package com.prgms.VoucherApp.domain.wallet.model.strategy;

import com.prgms.VoucherApp.domain.voucher.model.VoucherService;
import com.prgms.VoucherApp.domain.wallet.model.WalletService;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;

public interface WalletCommandStrategy {

    void execute(Input input, Output output, WalletService walletService, VoucherService voucherService);
}
