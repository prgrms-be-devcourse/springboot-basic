package org.programers.vouchermanagement;

import org.programers.vouchermanagement.member.presentation.MemberConsoleController;
import org.programers.vouchermanagement.view.DomainType;
import org.programers.vouchermanagement.view.InputView;
import org.programers.vouchermanagement.view.OutputView;
import org.programers.vouchermanagement.voucher.presentation.VoucherConsoleController;
import org.programers.vouchermanagement.wallet.presentation.WalletConsoleController;
import org.springframework.boot.CommandLineRunner;

public class VoucherManagementController implements CommandLineRunner {

    private final MemberConsoleController memberConsoleController;
    private final VoucherConsoleController voucherConsoleController;
    private final WalletConsoleController walletConsoleController;

    public VoucherManagementController(MemberConsoleController memberConsoleController,
                                       VoucherConsoleController voucherConsoleController,
                                       WalletConsoleController walletConsoleController) {
        this.memberConsoleController = memberConsoleController;
        this.voucherConsoleController = voucherConsoleController;
        this.walletConsoleController = walletConsoleController;
    }

    @Override
    public void run(String... args) throws Exception {
        boolean isRunning = true;
        while(isRunning) {
            OutputView.outputDomainType();

            DomainType type = InputView.inputDomainType();
            if (type.isVoucher()) {
                voucherConsoleController.run();
                continue;
            }

            if (type.isMember()) {
                memberConsoleController.run();
                continue;
            }

            if (type.isWallet()) {
                walletConsoleController.run();
                continue;
            }

            if (type.isExit()) {
                isRunning = false;
            }
        }
    }
}
