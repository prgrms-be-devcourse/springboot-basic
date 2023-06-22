package org.programers.vouchermanagement.voucher.presentation;

import org.programers.vouchermanagement.member.application.MemberService;
import org.programers.vouchermanagement.member.dto.MembersResponse;
import org.programers.vouchermanagement.voucher.application.VoucherService;
import org.programers.vouchermanagement.voucher.domain.FixedAmountPolicy;
import org.programers.vouchermanagement.voucher.domain.PercentDiscountPolicy;
import org.programers.vouchermanagement.voucher.dto.VoucherCreationRequest;
import org.programers.vouchermanagement.voucher.dto.VoucherResponse;
import org.programers.vouchermanagement.voucher.dto.VouchersResponse;
import org.programers.vouchermanagement.view.Command;
import org.programers.vouchermanagement.view.DiscountPolicyType;
import org.programers.vouchermanagement.view.InputView;
import org.programers.vouchermanagement.view.OutputView;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController implements CommandLineRunner {

    private final VoucherService voucherService;
    private final MemberService memberService;

    public VoucherController(VoucherService voucherService, MemberService memberService) {
        this.voucherService = voucherService;
        this.memberService = memberService;
    }

    @Override
    public void run(String... args) throws Exception {
        boolean isRunning = true;
        while(isRunning) {
            OutputView.outputCommand();

            Command command = InputView.inputCommand();
            if (command.isBlacklist()) {
                MembersResponse response = memberService.findAll();
                OutputView.outputMembers(response);
                continue;
            }

            if (command.isCreateVoucher()) {
                createVoucher();
                continue;
            }

            if (command.isListVoucher()) {
                VouchersResponse response = voucherService.findAll();
                OutputView.outputVouchers(response);
                continue;
            }

            if (command.isExit()) {
                isRunning = false;
            }
        }
    }

    private void createVoucher() {
        OutputView.outputDiscountPolicyType();
        DiscountPolicyType type = InputView.inputDiscountPolicy();
        if (type.isAmount()) {
            VoucherCreationRequest request = new VoucherCreationRequest(new FixedAmountPolicy());
            VoucherResponse response = voucherService.save(request);
            return;
        }

        if (type.isPercent()) {
            VoucherCreationRequest request = new VoucherCreationRequest(new PercentDiscountPolicy());
            VoucherResponse response = voucherService.save(request);
            return;
        }
    }
}
