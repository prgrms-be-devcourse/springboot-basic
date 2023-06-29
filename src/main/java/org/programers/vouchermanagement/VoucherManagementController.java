package org.programers.vouchermanagement;

import org.programers.vouchermanagement.member.domain.MemberStatus;
import org.programers.vouchermanagement.member.presentation.MemberController;
import org.programers.vouchermanagement.view.Command;
import org.programers.vouchermanagement.view.DomainType;
import org.programers.vouchermanagement.view.InputView;
import org.programers.vouchermanagement.view.OutputView;
import org.programers.vouchermanagement.voucher.presentation.VoucherController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

@Profile("!test")
@Controller
public class VoucherManagementController implements CommandLineRunner {

    private final MemberController memberController;
    private final VoucherController voucherController;

    public VoucherManagementController(MemberController memberController, VoucherController voucherController) {
        this.memberController = memberController;
        this.voucherController = voucherController;
    }

    @Override
    public void run(String... args) throws Exception {
        boolean isRunning = true;
        while(isRunning) {
            OutputView.outputCommand();

            DomainType type = InputView.inputDomainType();
            if (type.isVoucher()) {
                memberController.findAllInBlacklist(MemberStatus.BLACK);
                continue;
            }

            if (type.isMember()) {
                voucherController.save();
                continue;
            }

            if (type.isWallet()) {
                voucherController.findAll();
                continue;
            }

            if (type.isExit()) {
                isRunning = false;
            }
        }
    }
}
