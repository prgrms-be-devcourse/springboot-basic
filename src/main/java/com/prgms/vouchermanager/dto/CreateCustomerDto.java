package com.prgms.vouchermanager.dto;

import lombok.Getter;

@Getter
public class CreateCustomerDto {
    private final String name;
    private final String email;
    private final int blackList;

    public CreateCustomerDto(String name, String email, int blackList) {
        this.name = name;
        this.email = email;

        this.blackList = blackList;
    }
}
