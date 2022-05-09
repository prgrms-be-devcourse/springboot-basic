package org.prgms.voucheradmin.domain.customer.dto;

import static org.prgms.voucheradmin.global.util.Util.isValidEmail;

import java.time.LocalDateTime;
import java.util.UUID;

import org.prgms.voucheradmin.domain.customer.entity.Customer;
import org.prgms.voucheradmin.global.exception.customexception.WrongInputException;

public class CustomerCreateReqDto {
    private String name;
    private String email;

    public CustomerCreateReqDto(String name, String email) {
        if(name.isBlank()) {
            throw new WrongInputException("wrong name input");
        }

        if(isValidEmail(email)) {
            this.name = name;
            this.email = email;
        }else{
            throw new WrongInputException("wrong email input");
        }
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Customer toEntity() {
        return Customer.builder()
                .customerId(UUID.randomUUID())
                .name(name)
                .email(email)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
