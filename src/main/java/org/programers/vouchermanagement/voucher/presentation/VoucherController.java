package org.programers.vouchermanagement.voucher.presentation;

import org.programers.vouchermanagement.view.Command;
import org.programers.vouchermanagement.voucher.application.VoucherService;
import org.programers.vouchermanagement.voucher.dto.request.VoucherCreationRequest;
import org.programers.vouchermanagement.voucher.dto.request.VoucherUpdateRequest;
import org.programers.vouchermanagement.voucher.dto.response.VouchersResponse;
import org.programers.vouchermanagement.view.DiscountPolicyType;
import org.programers.vouchermanagement.view.InputView;
import org.programers.vouchermanagement.view.OutputView;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public void run() {
        OutputView.outputCommand();
        Command command = InputView.inputCommand();

        if (command.isCreate()) {
            save();
            return;
        }

        if(command.isRead()) {
            VouchersResponse response = voucherService.findAll();
            OutputView.outputVouchers(response);
            return;
        }

        if (command.isUpdate()) {
            update();
            return;
        }

        if (command.isDelete()) {
            OutputView.outputUUIDComment();
            UUID id = InputView.inputUUID();
            voucherService.deleteById(id);
        }
    }

    private void save() {
        OutputView.outputDiscountPolicyType();
        DiscountPolicyType type = InputView.inputDiscountPolicy();

        OutputView.outputCommentAboutPolicy();
        int value = InputView.inputValueOfPolicy();

        VoucherCreationRequest request = new VoucherCreationRequest(type.createPolicy(value), type.getType());
        voucherService.save(request);
    }

    private void update() {
        OutputView.outputUUIDComment();
        UUID id = InputView.inputUUID();

        OutputView.outputDiscountPolicyType();
        DiscountPolicyType type = InputView.inputDiscountPolicy();

        OutputView.outputCommentAboutPolicy();
        int value = InputView.inputValueOfPolicy();
        voucherService.update(new VoucherUpdateRequest(id, type.createPolicy(value), type.getType()));
    }
}
