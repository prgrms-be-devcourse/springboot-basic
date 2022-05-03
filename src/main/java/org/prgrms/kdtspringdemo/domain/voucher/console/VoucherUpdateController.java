package org.prgrms.kdtspringdemo.domain.voucher.console;

import org.prgrms.kdtspringdemo.domain.console.Input;
import org.prgrms.kdtspringdemo.domain.console.Output;
import org.prgrms.kdtspringdemo.domain.voucher.VoucherService;
import org.prgrms.kdtspringdemo.domain.voucher.data.Voucher;
import org.prgrms.kdtspringdemo.domain.voucher.type.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherUpdateController {
    private static final Logger logger = LoggerFactory.getLogger(VoucherChooseController.class);
    private final Output output;
    private final Input input;
    private final VoucherService voucherService;

    public VoucherUpdateController(Output output, Input input, VoucherService voucherService) {
        this.output = output;
        this.input = input;
        this.voucherService = voucherService;
    }


    public void updateVoucherWithType(Voucher willUpdateVoucher, VoucherType voucherType) {
        switch (voucherType) {
            case FIXED -> {
                VoucherType.FIXED.writeStateInfo();
                System.out.println(output.FixedDiscountAmountMessage());
                // 입력값 받아오면서 입력 타입 검증
                int amount = input.inputDiscountAmount(voucherType);
                // mysql db 에 update
                voucherService.updateAmount(willUpdateVoucher, voucherType, amount);
            }
            case PERCENT -> {
                VoucherType.PERCENT.writeStateInfo();
                System.out.println(output.PercentDiscountAmountMessage());
                // 입력값 받아오면서 입력 타입 검증
                int amount = input.inputDiscountAmount(voucherType);
                // mysql db 에 update
                voucherService.updateAmount(willUpdateVoucher, voucherType, amount);
            }
            case None -> VoucherType.None.writeStateInfo();
        }
    }
}
