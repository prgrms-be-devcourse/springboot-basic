package org.programers.vouchermanagement.voucher.presentation;

import org.programers.vouchermanagement.voucher.application.VoucherService;
import org.programers.vouchermanagement.voucher.domain.FixedAmountPolicy;
import org.programers.vouchermanagement.voucher.domain.PercentDiscountPolicy;
import org.programers.vouchermanagement.voucher.dto.VoucherCreationRequest;
import org.programers.vouchermanagement.voucher.dto.VoucherResponse;
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
        if (type.isAmount()) {
            VoucherCreationRequest request = new VoucherCreationRequest(new FixedAmountPolicy());
            VoucherResponse response = voucherService.save(request);
            return;
        }

        if (type.isPercent()) {
            VoucherCreationRequest request = new VoucherCreationRequest(new PercentDiscountPolicy());
            VoucherResponse response = voucherService.save(request);
        }
    }

    public void findAll() {
        VouchersResponse response = voucherService.findAll();
        OutputView.outputVouchers(response);
    }
}
