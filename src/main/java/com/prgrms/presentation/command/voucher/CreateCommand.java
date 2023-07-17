package com.prgrms.presentation.command.voucher;

import com.prgrms.model.voucher.VoucherType;
import com.prgrms.presentation.Power;
import com.prgrms.presentation.command.Command;
import com.prgrms.presentation.message.GuideMessage;
import com.prgrms.presentation.view.Input;
import com.prgrms.presentation.view.Output;
import com.prgrms.service.voucher.VoucherService;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CreateCommand implements Command {

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;

    public CreateCommand(Input input, Output output, VoucherService voucherService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
    }

    @Override
    public Power execute() {
        VoucherType voucherType = guideCreateVoucher();
        double discountAmount = guideVoucherPolicy(voucherType);

        voucherService.createVoucher(voucherType, discountAmount);

        return Power.ON;
    }

    private VoucherType guideCreateVoucher() {
        Arrays.stream(VoucherType.values())
                .forEach(voucherPolicy -> output.write(voucherPolicy.voucherPolicyOptionGuide()));

        String option = input.enterOption();

        return VoucherType.findByType(option);
    }

    private double guideVoucherPolicy(VoucherType voucherType) {
        output.write(voucherType.discountGuide());
        double discountAmount = input.enterDiscount();

        output.write(GuideMessage.COMPLETE_CREATE.toString());

        return discountAmount;
    }

}
