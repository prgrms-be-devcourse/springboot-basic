package com.prgrms.presentation.command.wallet;

import com.prgrms.dto.voucher.VoucherResponse;
import com.prgrms.presentation.Power;
import com.prgrms.presentation.command.Command;
import com.prgrms.presentation.message.GuideMessage;
import com.prgrms.presentation.view.Input;
import com.prgrms.presentation.view.Output;
import com.prgrms.service.wallet.WalletService;

import java.util.List;

public class VoucherListCommand implements Command {

    private final Input input;
    private final Output output;
    private final WalletService walletService;

    public VoucherListCommand(Input input, Output output, WalletService walletService) {
        this.input = input;
        this.output = output;
        this.walletService = walletService;
    }

    @Override
    public Power execute() {

        output.write(GuideMessage.WALLET_LIST.toString());
        int customerId = input.enterID();

        List<VoucherResponse> vouchers = walletService.voucherList(customerId);
        vouchers.forEach(v -> output.write(v.toString()));

        return Power.ON;
    }

}
