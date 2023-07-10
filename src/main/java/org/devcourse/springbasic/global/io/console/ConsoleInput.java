package org.devcourse.springbasic.global.io.console;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.devcourse.springbasic.domain.voucher.domain.MenuType;
import org.devcourse.springbasic.domain.voucher.domain.VoucherType;
import org.devcourse.springbasic.global.io.input.Input;
import org.devcourse.springbasic.global.validator.MenuValidator;
import org.devcourse.springbasic.global.validator.Validator;
import org.devcourse.springbasic.global.validator.VoucherMenuValidator;

public class ConsoleInput implements Input {

    private final TextIO textIO = TextIoFactory.getTextIO();

    @Override
    public MenuType menu() {

        String input = textIO.newStringInputReader()
                .read("메뉴를 입력하세요: ");

        Validator<String> menuValidator = new MenuValidator<>();
        boolean isValid = menuValidator.validate(input);
        if (isValid) {
            return MenuType.findSolveStateByInput(input);
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
