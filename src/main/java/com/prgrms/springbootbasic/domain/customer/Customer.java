package com.prgrms.springbootbasic.domain.customer;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer {

    private static final Pattern ENGLISH_PATTERN = Pattern.compile("^[a-zA-Z]*$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$");

    private final UUID customerId;
    private String name;
    private String email;
    private final LocalDateTime createAt;

    public Customer(UUID customerId, String name, String email, LocalDateTime createAt) {
        this.customerId = customerId;
        this.name = validateName(name);
        this.email = validateEmail(email);
        this.createAt = createAt;
    }

    public String validateName(String name) {
        Matcher match = ENGLISH_PATTERN.matcher(name);

        if (!match.matches()) {
            throw new IllegalArgumentException("영어 입력이 아니여서 에러가 납니다.!");
        }
        return name;
    }

    public String validateEmail(String email) {
        Matcher match = EMAIL_PATTERN.matcher(email);

        if (!match.matches()) {
            throw new IllegalArgumentException("이메일 형식이 아니여서 에러가 납니다.!");
        }
        return email;
    }
}

