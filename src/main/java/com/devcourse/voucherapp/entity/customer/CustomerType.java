package com.devcourse.voucherapp.entity.customer;

import static java.text.MessageFormat.format;

import com.devcourse.voucherapp.utils.exception.customer.CustomerTypeInputException;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum CustomerType {
    NORMAL("1", "일반 고객"),
    BLACK("2", "블랙리스트 고객");

    private static final Map<String, CustomerType> CUSTOMER_TYPES = Collections.unmodifiableMap(Stream.of(values())
            .collect(Collectors.toMap(CustomerType::getNumber, Function.identity())));

    private final String number;
    private final String name;

    CustomerType(String number, String name) {
        this.number = number;
        this.name = name;
    }

    public static CustomerType from(String customerTypeNumber) {
        if (CUSTOMER_TYPES.containsKey(customerTypeNumber)) {
            return CUSTOMER_TYPES.get(customerTypeNumber);
        }

        throw new CustomerTypeInputException(customerTypeNumber);
    }

    @Override
    public String toString() {
        return format("{0}. {1}", number, name);
    }
}
