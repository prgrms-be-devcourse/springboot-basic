package com.prgrms.presentation.command.wallet;

import com.prgrms.dto.wallet.WalletRequest;
import com.prgrms.presentation.Power;
import com.prgrms.presentation.command.Command;
import com.prgrms.presentation.message.GuideMessage;
import com.prgrms.presentation.view.Input;
import com.prgrms.presentation.view.Output;
import com.prgrms.service.wallet.WalletService;
import org.springframework.stereotype.Component;

@Component
public class TakeVoucherCommand implements Command {

    private final Output output;
    private final Input input;
    private final WalletService walletService;

    public TakeVoucherCommand(Output output, Input input, WalletService walletService) {
        this.output = output;
        this.input = input;
        this.walletService = walletService;
    }

    @Override
    public Power execute() {
        WalletRequest walletRequest = guideTakeVoucherFromCustomer();
        walletService.takeVoucher(walletRequest);

        return Power.ON;
    }

    private WalletRequest guideTakeVoucherFromCustomer() {
        output.write(GuideMessage.TAKE_VOUCHER.toString());
        int customerId = input.enterID();

        output.write(GuideMessage.TAKE_FROM_VOUCHER_ID.toString());
        int voucherId = input.enterID();

        return new WalletRequest(customerId, voucherId);
    }

}
