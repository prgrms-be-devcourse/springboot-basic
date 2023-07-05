package org.programers.vouchermanagement;

import org.programers.vouchermanagement.member.presentation.MemberController;
import org.programers.vouchermanagement.view.DomainType;
import org.programers.vouchermanagement.view.InputView;
import org.programers.vouchermanagement.view.OutputView;
import org.programers.vouchermanagement.voucher.presentation.VoucherController;
import org.programers.vouchermanagement.wallet.presentation.WalletController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

@Profile("!test")
@Controller
public class VoucherManagementController implements CommandLineRunner {

    private final MemberController memberController;
    private final VoucherController voucherController;
    private final WalletController walletController;

    public VoucherManagementController(MemberController memberController,
                                       VoucherController voucherController,
                                       WalletController walletController) {
        this.memberController = memberController;
        this.voucherController = voucherController;
        this.walletController = walletController;
    }

    @Override
    public void run(String... args) throws Exception {
        boolean isRunning = true;
        while(isRunning) {
            OutputView.outputDomainType();

            DomainType type = InputView.inputDomainType();
            if (type.isVoucher()) {
                voucherController.run();
                continue;
            }

            if (type.isMember()) {
                memberController.run();
                continue;
            }

            if (type.isWallet()) {
                walletController.run();
                continue;
            }

            if (type.isExit()) {
                isRunning = false;
            }
        }
    }
}
