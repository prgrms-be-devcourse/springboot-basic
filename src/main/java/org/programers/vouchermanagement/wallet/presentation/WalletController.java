package org.programers.vouchermanagement.wallet.presentation;

import org.programers.vouchermanagement.view.Command;
import org.programers.vouchermanagement.view.InputView;
import org.programers.vouchermanagement.view.OutputView;
import org.programers.vouchermanagement.view.WalletReadOption;
import org.programers.vouchermanagement.wallet.application.WalletService;
import org.programers.vouchermanagement.wallet.dto.WalletCreationRequest;
import org.programers.vouchermanagement.wallet.dto.WalletsResponse;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    public void run() {
        OutputView.outputCommand();
        Command command = InputView.inputCommand();

        if (command.isCreate()) {
            save();
            return;
        }

        if(command.isRead()) {
            read();
            return;
        }

        if (command.isDelete()) {
            delete();
        }
    }

    private void save() {
        OutputView.outputVoucherIdComment();
        UUID voucherId = InputView.inputUUID();

        OutputView.outputMemberIdComment();
        UUID memberId = InputView.inputUUID();

        walletService.save(new WalletCreationRequest(voucherId, memberId));
    }

    private void read() {
        OutputView.outputWalletReadOption();
        WalletReadOption option = InputView.inputWalletReadOption();

        if (option.isVoucher()) {
            OutputView.outputVoucherIdComment();
            UUID id = InputView.inputUUID();

            WalletsResponse response = walletService.findByVoucherId(id);
            OutputView.outputWallets(response);
            return;
        }

        if (option.isMember()) {
            OutputView.outputMemberIdComment();
            UUID id = InputView.inputUUID();

            WalletsResponse response = walletService.findByMemberId(id);
            OutputView.outputWallets(response);
        }
    }

    private void delete() {
        OutputView.outputUUIDComment();
        UUID id = InputView.inputUUID();
        walletService.deleteById(id);
    }
}
