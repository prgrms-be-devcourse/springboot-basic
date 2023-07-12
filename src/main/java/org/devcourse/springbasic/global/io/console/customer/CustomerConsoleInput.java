package org.devcourse.springbasic.global.io.console.customer;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.devcourse.springbasic.domain.customer.domain.CustomerMenuType;
import org.devcourse.springbasic.domain.customer.dto.CustomerDto;
import org.devcourse.springbasic.global.io.input.customer.CustomerInput;
import org.devcourse.springbasic.global.validator.MenuValidator;
import org.devcourse.springbasic.global.validator.Validator;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerConsoleInput implements CustomerInput {

    private final TextIO textIO = TextIoFactory.getTextIO();

    @Override
    public CustomerMenuType menu() {
        String input = textIO.newStringInputReader()
                .read("메뉴를 입력하세요: ");

        Validator<String> menuValidator = new MenuValidator<>();
        boolean isValid = menuValidator.validate(input);
        if (isValid) {
            return CustomerMenuType.findCustomerMenuByInput(input);
        } else {
            throw new IllegalArgumentException("잘못된 메뉴 입력입니다.");
        }
    }

    @Override
    public CustomerDto.SaveRequestDto signUp() {
        String name = textIO.newStringInputReader()
                .read("이름을 입력하세요: ");

        String email = textIO.newStringInputReader()
                .read("이메일을 입력하세요: ");

        return new CustomerDto.SaveRequestDto(UUID.randomUUID(), name, email, LocalDateTime.now());
    }
}
