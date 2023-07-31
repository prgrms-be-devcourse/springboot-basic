package org.devcourse.springbasic.global.io.console;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.devcourse.springbasic.domain.DomainType;
import org.devcourse.springbasic.global.io.input.AppInput;
import org.devcourse.springbasic.global.validator.MenuValidator;
import org.devcourse.springbasic.global.validator.Validator;

public class AppConsoleInput implements AppInput {

    private final TextIO textIO = TextIoFactory.getTextIO();

    @Override
    public DomainType menu() {
        String input = textIO.newStringInputReader()
                .read("번호로 입력하세요: ");

        Validator<String> menuValidator = new MenuValidator<>();
        boolean isValid = menuValidator.validate(input);
        if (isValid) {
            return DomainType.findDomainByInputNum(input);
        } else {
            throw new IllegalArgumentException("잘못된 메뉴 입력입니다.");
        }
    }
}
