package org.programers.vouchermanagement.voucher.presentation;

import org.programers.vouchermanagement.voucher.application.VoucherService;
import org.programers.vouchermanagement.voucher.dto.VoucherCreationRequest;
import org.programers.vouchermanagement.voucher.dto.VouchersResponse;
import org.programers.vouchermanagement.view.DiscountPolicyType;
import org.programers.vouchermanagement.view.InputView;
import org.programers.vouchermanagement.view.OutputView;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public void save() {
        OutputView.outputDiscountPolicyType();
        DiscountPolicyType type = InputView.inputDiscountPolicy();

        OutputView.outputCommentAboutPercentOfPolicy();
        int value = InputView.inputValueOfPolicy();

        VoucherCreationRequest request = new VoucherCreationRequest(type.createPolicy(value));
        voucherService.save(request);
    }

    public void findAll() {
        VouchersResponse response = voucherService.findAll();
        OutputView.outputVouchers(response);
    }
}
