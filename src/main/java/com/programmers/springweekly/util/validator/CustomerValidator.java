package com.programmers.springweekly.util.validator;

import com.programmers.springweekly.domain.customer.CustomerType;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public final class CustomerValidator {

    private static final Pattern englishPattern = Pattern.compile("^[a-zA-Z]*$");
    private static final Pattern emailPattern = Pattern.compile("^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$");

    public static void validateInputCustomerInfo(String name, String email) {
        validateName(name);
        validateEmail(email);
    }

    public static void validateCustomer(String customerName, String customerEmail, CustomerType customerType) {
        validateName(customerName);
        validateEmail(customerEmail);
        validateType(customerType);
    }

    private static void validateName(String input) {
        Matcher match = englishPattern.matcher(input);

        if (!match.matches()) {
            log.warn("고객이 입력한 문자열 {} 이 영어가 아니어서 예외 발생", input);
            throw new IllegalArgumentException("Input : " + input + ", 입력하신 것은 영어가 아닙니다.");
        }
    }

    private static void validateEmail(String input) {
        Matcher match = emailPattern.matcher(input);

        if (!match.matches()) {
            log.warn("고객이 입력한 문자열 {} 이 이메일이 아니어서 예외 발생", input);
            throw new IllegalArgumentException("Input : " + input + "입력하신 것은 이메일이 아닙니다.");
        }
    }

    private static void validateType(CustomerType customerType) {
        Arrays.stream(CustomerType.values())
                .filter((type) -> type == customerType)
                .findAny()
                .orElseThrow(() -> {
                    log.warn("고객이 입력한 타입 {} 이 고객 타입이 아니어서 예외 발생", customerType);
                    return new IllegalArgumentException("찾는 고객 타입이 없습니다.");
                });
    }

}
