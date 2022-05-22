package org.prgms.voucheradmin.domain.customer.dto;

import org.prgms.voucheradmin.domain.customer.entity.Customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerUpdateReqDto {
    private String name;

    public CustomerUpdateReqDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
