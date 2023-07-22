package com.prgrms.presentation.command.voucher;

import com.prgrms.common.KeyGenerator;
import com.prgrms.presentation.Power;
import com.prgrms.presentation.command.Command;
import com.prgrms.presentation.message.GuideMessage;
import com.prgrms.presentation.view.Input;
import com.prgrms.presentation.view.Output;
import com.prgrms.voucher.model.VoucherType;
import com.prgrms.voucher.service.VoucherService;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.springframework.stereotype.Component;

@Component
public class CreateCommand implements Command {

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final KeyGenerator keyGenerator;

    public CreateCommand(Input input, Output output, VoucherService voucherService,
            KeyGenerator keyGenerator) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
        this.keyGenerator = keyGenerator;
    }

    @Override
    public Power execute() {
        int id = keyGenerator.make();
        LocalDateTime createdAt = LocalDateTime.now();
        VoucherType voucherType = guideCreateVoucher();
        double discountAmount = guideVoucherPolicy(voucherType);

        voucherService.createVoucher(id, voucherType, discountAmount,createdAt);

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
