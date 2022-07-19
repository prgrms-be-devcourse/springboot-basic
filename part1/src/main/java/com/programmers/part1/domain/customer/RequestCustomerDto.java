package com.programmers.part1.domain.customer;

import lombok.Getter;

@Getter
public class RequestCustomerDto {

   private final String name;
   private final String email;

    public RequestCustomerDto(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
