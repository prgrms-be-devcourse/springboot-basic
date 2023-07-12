package org.devcourse.springbasic.global.io.console.voucher;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.devcourse.springbasic.domain.voucher.domain.VoucherMenuType;
import org.devcourse.springbasic.domain.voucher.domain.VoucherType;
import org.devcourse.springbasic.global.io.input.voucher.VoucherInput;
import org.devcourse.springbasic.global.validator.MenuValidator;
import org.devcourse.springbasic.global.validator.Validator;
import org.devcourse.springbasic.global.validator.VoucherMenuValidator;

public class VoucherConsoleInput implements VoucherInput {

    private final TextIO textIO = TextIoFactory.getTextIO();

    @Override
    public VoucherMenuType menu() {
        String input = textIO.newStringInputReader()
                .read("메뉴를 입력하세요: ");

        Validator<String> menuValidator = new MenuValidator<>();
        boolean isValid = menuValidator.validate(input);
        if (isValid) {
            return VoucherMenuType.findVoucherMenuByInput(input);
        } else {
            throw new IllegalArgumentException("잘못된 메뉴 입력입니다.");
        }
    }

    @Override
    public VoucherType voucherMenu() {
        String input = textIO.newStringInputReader()
                .read("바우처를 선택하세요. (번호 입력)");

        Validator<String> voucherMenuValidator = new VoucherMenuValidator();
        boolean isValid = voucherMenuValidator.validate(input);

        if (isValid) {
            return VoucherType.findByMenuNum(Integer.parseInt(input));
        } else {
            throw new IllegalArgumentException("잘못된 메뉴 입력입니다.");
        }
    }
}
