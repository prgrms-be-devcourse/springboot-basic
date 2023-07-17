package com.prgrms.presentation.command.wallet;

import com.prgrms.dto.wallet.WalletRequest;
import com.prgrms.model.KeyGenerator;
import com.prgrms.presentation.Power;
import com.prgrms.presentation.command.Command;
import com.prgrms.presentation.message.GuideMessage;
import com.prgrms.presentation.view.Input;
import com.prgrms.presentation.view.Output;
import com.prgrms.service.wallet.WalletService;
import org.springframework.stereotype.Component;

@Component
public class GiveVoucherCommand implements Command {

    private final Output output;
    private final Input input;
    private final WalletService walletService;
    private final KeyGenerator keyGenerator;

    public GiveVoucherCommand(Output output, Input input, WalletService walletService,
            KeyGenerator keyGenerator) {
        this.output = output;
        this.input = input;
        this.walletService = walletService;
        this.keyGenerator = keyGenerator;
    }

    @Override
    public Power execute() {
        WalletRequest walletRequest = guideGiveVoucherToCustomer();
        int id = keyGenerator.make();
        walletService.giveVoucher(id, walletRequest);

        return Power.ON;
    }

    private WalletRequest guideGiveVoucherToCustomer() {
        output.write(GuideMessage.GIVE_VOUCHER.toString());
        int customerId = input.enterID();

        output.write(GuideMessage.WANT_TO_VOUCHER_ID.toString());
        int voucherId = input.enterID();

        return new WalletRequest(customerId, voucherId);
    }

}
