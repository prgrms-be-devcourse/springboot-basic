package com.devcourse.voucherapp.entity.customer;

import static com.devcourse.voucherapp.entity.customer.CustomerType.NORMAL;

import com.devcourse.voucherapp.exception.CustomerException;
import com.devcourse.voucherapp.exception.ExceptionRule;
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

    public static Customer from(UUID id, CustomerType type, String nickname) {
        return new Customer(id, type, nickname);
    }

    private void validateIsAlphaNumeric(String nickname) {
        if (!nickname.matches(ALPHA_NUMERIC_REGEX)) {
            throw new CustomerException(ExceptionRule.CUSTOMER_NICKNAME_INVALID, nickname);
        }
    }
}
