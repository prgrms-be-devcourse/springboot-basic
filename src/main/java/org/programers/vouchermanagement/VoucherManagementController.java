package org.programers.vouchermanagement;

import org.programers.vouchermanagement.member.presentation.MemberController;
import org.programers.vouchermanagement.view.Command;
import org.programers.vouchermanagement.view.InputView;
import org.programers.vouchermanagement.view.OutputView;
import org.programers.vouchermanagement.voucher.presentation.VoucherController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

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

            Command command = InputView.inputCommand();
            if (command.isBlacklist()) {
                memberController.findAllBlackList();
                continue;
            }

            if (command.isCreateVoucher()) {
                voucherController.save();
                continue;
            }

            if (command.isListVoucher()) {
                voucherController.findAll();
                continue;
            }

            if (command.isExit()) {
                isRunning = false;
            }
        }
    }
}
