package com.prgrms.springbootbasic.controller;

import com.prgrms.springbootbasic.domain.PercentDiscountVoucher;
import com.prgrms.springbootbasic.domain.Voucher;
import com.prgrms.springbootbasic.io.Input;
import com.prgrms.springbootbasic.io.Output;
import com.prgrms.springbootbasic.service.VoucherService;
import java.util.UUID;

public class PercentDiscountVoucherController {
    private final Input input;
    private final Output output;
    private final VoucherService voucherService;

    public PercentDiscountVoucherController(Input input, Output output, VoucherService voucherService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
    }

    public void createPercentDiscountVoucher() {
        try {
            String percentStr = input.readString("PercentDiscountVoucher의 percent를 입력해주세요.(할인 퍼센트만 입력,%표시 제외)");
            double percent = Double.parseDouble(percentStr);
            if (percent < 0 || percent > 100) {
                output.println("허용되지 않는 퍼센트 값입니다. 0과 100 사이의 값으로 입력해주세요.");
                return;
            }
            Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), (long) percent);
            voucherService.createPercentDiscountVoucher(percent);

            output.println("새로운 PercentDiscountVoucher가 생성되었습니다.");
            output.println("생성된 PercentDiscountVoucher: " + voucher.getDiscount());
        } catch (NumberFormatException e) {
            output.println("유효하지 않은 입력입니다. 숫자로 입력해주세요.");
        } catch (Exception e) {
            output.println("PercentDiscountVoucher 생성 중 오류가 발생했습니다.");
        }
    }
}