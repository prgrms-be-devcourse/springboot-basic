package com.programmers.kdtspringorder.command;

import com.programmers.kdtspringorder.io.Input;
import com.programmers.kdtspringorder.io.Output;
import com.programmers.kdtspringorder.voucher.VoucherService;
import com.programmers.kdtspringorder.voucher.VoucherType;
import com.programmers.kdtspringorder.voucher.domain.Voucher;

public record CreateCommandAction(Input input,
                                  Output output,
                                  VoucherService voucherService) implements CommandAction {

    @Override
    public void act() {
        output.printMessage("2000원 쿠폰 생성은 'FIXED', 10% 쿠폰 생성은 'PERCENT'를 선택해주세요");
        String type = input.inputText();
        if (isWrongType(type)) {
            output.printMessage("잘못 입력 하셨습니다");
        }
        Voucher voucher = createVoucher(type, 20);
        output.printMessage("쿠폰 생성에 성공하였습니다");
    }

    private Voucher createVoucher(String type, long value) {
        return voucherService.createVoucher(type, value);
    }

    private boolean isWrongType(String type) {
        if (type == null) return false;
        return isNotValidType(type);
    }

    private boolean isNotValidType(String type) {
        return !VoucherType.isValidType(type);
    }
}
