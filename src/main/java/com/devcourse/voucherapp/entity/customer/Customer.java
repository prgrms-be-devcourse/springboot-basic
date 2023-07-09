package com.devcourse.voucherapp.entity.customer;

import static com.devcourse.voucherapp.entity.CustomerType.NORMAL;

import com.devcourse.voucherapp.entity.CustomerType;
import com.devcourse.voucherapp.exception.CustomerInputException;
import java.util.UUID;
import lombok.Getter;

@Getter
public class Customer {

    private static final String ALPHA_NUMERIC_REGEX = "^(?:[a-z]+|\\d+|[a-z\\d]+)$";

    private final UUID id;
    private final CustomerType type;
    private final String nickname;

    public Customer(UUID id, CustomerType type, String nickname) {
        validateIsAlphaNumeric(nickname);

        this.id = id;
        this.type = type;
        this.nickname = nickname;
    }

    public static Customer from(String nickname) {
        return new Customer(UUID.randomUUID(), NORMAL, nickname);
    }

    private void validateIsAlphaNumeric(String nickname) {
        if (isNotAlphaNumeric(nickname)) {
            throw new CustomerInputException(nickname);
        }
    }

    private boolean isNotAlphaNumeric(String nickname) {
        return !nickname.matches(ALPHA_NUMERIC_REGEX);
    }
}
