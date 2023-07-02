package org.prgms.vouchermanagement.customer.domain.entity;

import lombok.Getter;
import org.prgms.vouchermanagement.customer.exception.CustomerException;
import org.prgms.vouchermanagement.global.constant.ExceptionMessageConstant;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Customer {

    private final UUID customerId;
    private String name;
    private final String email;
    private final LocalDateTime createdAt;

    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new CustomerException(ExceptionMessageConstant.BLANK_CUSTOMER_NAME_EXCEPTION);
        }
    }

    public void changeName(String name) {
        validateName(name);
        this.name = name;
    }
}
