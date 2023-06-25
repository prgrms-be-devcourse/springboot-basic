package com.prgrms.springbootbasic.controller;

import com.prgrms.springbootbasic.domain.Voucher;
import com.prgrms.springbootbasic.io.Input;
import com.prgrms.springbootbasic.io.Output;
import com.prgrms.springbootbasic.service.VoucherService;

public class FixedDiscountVoucherController {
    private final Input input;
    private final Output output;
    private final VoucherService voucherService;

    public FixedDiscountVoucherController(Input input, Output output, VoucherService voucherService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
    }

    public void createFixedDiscountVoucher() {
        try {
            String amountStr = input.readString("FixedAmountVoucher의 Discount를 입력해주세요.(할인 금액만 입력, ₩표시 제외)");
            long amount = Long.parseLong(amountStr);
            Voucher voucher = voucherService.createFixedDiscountVoucher(amount);

            output.println("새로운 FixedAmountVoucher가 생성되었습니다.");
            output.println("생성된 FixedVoucher: " + voucher.getDiscount() + "입니다.");
        } catch (NumberFormatException e) {
            output.println("유효하지 않은 입력입니다. 숫자로 입력해주세요.");
        } catch (Exception e) {
            output.println("FixedAmountVoucher 생성 중 오류가 발생했습니다.");
        }
    }
}
