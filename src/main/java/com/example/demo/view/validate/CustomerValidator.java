package com.example.demo.view.validate;

import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerValidator {

    private static final Pattern IS_AGE_REGEX = Pattern.compile("^([1-9]|[1-9][0-9]|100)$");
    private static final Pattern IS_NAME_REGEX = Pattern.compile("^.{1,20}$");

    public static void validateName(String input) {
        if (!IS_NAME_REGEX.matcher(input).matches()) {
            log.error("이름은 1이상 ~ 20글자 이하의 문자를 입력해주세요. 현재 입력 된 이름은 {} 입니다.", input);
            throw new IllegalArgumentException(String.format("[ERROR] 이름은 1이상 ~ 20글자 이하의 문자를 입력해주세요. 현재 입력 된 이름은 {%s} 입니다.", input));
        }
    }

    public static void validateAge(String input) {
        if (!IS_AGE_REGEX.matcher(input).matches()) {
            log.error("[ERROR] 나이는 1이상 ~ 100 이하의 숫자를 입력해주세요. 현재 입력 된 숫자는 {} 입니다.", input);
            throw new IllegalArgumentException(String.format("[ERROR] 나이는 1이상 ~ 100 이하의 숫자를 입력해주세요. 현재 입력 된 숫자는 {%s} 입니다.", input));
        }
    }
}
