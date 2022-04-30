package org.prgrms.kdtspringdemo.domain.voucher.console;

import org.prgrms.kdtspringdemo.domain.console.Input;
import org.prgrms.kdtspringdemo.domain.console.Output;
import org.prgrms.kdtspringdemo.domain.voucher.VoucherService;
import org.prgrms.kdtspringdemo.domain.voucher.data.Voucher;
import org.prgrms.kdtspringdemo.domain.voucher.type.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class VoucherChooseController {
    private static final Logger logger = LoggerFactory.getLogger(VoucherChooseController.class);
    private final Output output;
    private final Input input;
    private final VoucherService voucherService;

    public VoucherChooseController(Output output, Input input, VoucherService voucherService) {
        this.output = output;
        this.input = input;
        this.voucherService = voucherService;
    }


    // Voucher 타입 선택(Fixed, Percent)
    public void chooseVoucher() {
        System.out.println(output.chooseVoucherTypeMessage());
        VoucherType voucherType = input.inputVoucherType();

        switch (voucherType) {
            case FIXED -> {
                VoucherType.FIXED.writeStateInfo();
                System.out.println(output.FixedDiscountAmountMessage());
                // 입력값 받아오면서 입력 타입 검증
                int amount = input.inputDiscountAmount(voucherType);

                // mysql db 에 저장하기
                voucherService.saveVoucherInDB(amount, VoucherType.FIXED);
            }
            case PERCENT -> {
                VoucherType.PERCENT.writeStateInfo();
                System.out.println(output.PercentDiscountAmountMessage());
                // 입력값 받아오면서 입력 타입 검증
                int amount = input.inputDiscountAmount(voucherType);

                // mysql db 에 저장하기
                voucherService.saveVoucherInDB(amount, VoucherType.PERCENT);
            }
            case None -> VoucherType.None.writeStateInfo();
        }
    }

    public void showVoucherList() {
        List<Voucher> vouchers = voucherService.showVoucherList();
        vouchers.stream().forEach(System.out::println);
    }
}
