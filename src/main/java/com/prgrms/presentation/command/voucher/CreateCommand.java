package com.prgrms.presentation.command.voucher;

import com.prgrms.common.util.Generator;
import com.prgrms.presentation.Power;
import com.prgrms.presentation.command.Command;
import com.prgrms.presentation.message.GuideMessage;
import com.prgrms.presentation.view.Input;
import com.prgrms.presentation.view.Output;
import com.prgrms.voucher.model.VoucherType;
import com.prgrms.voucher.service.VoucherService;
import com.prgrms.voucher.service.dto.VoucherServiceCreateRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.springframework.stereotype.Component;

@Component
public class CreateCommand implements Command {

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final Generator generator;

    public CreateCommand(Input input, Output output, VoucherService voucherService,
            Generator generator) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
        this.generator = generator;
    }

    @Override
    public Power execute() {
        String id = generator.makeKey();
        LocalDateTime createdAt = generator.makeDate();
        VoucherType voucherType = guideCreateVoucher();
        double discountAmount = guideVoucherPolicy(voucherType);

        voucherService.createVoucher(id,
                new VoucherServiceCreateRequest(voucherType, discountAmount), createdAt);

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
