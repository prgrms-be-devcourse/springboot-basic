package com.programmers.springmission.customer.presentation.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerCreateRequest {

    private String name;
    private String email;

    public CustomerCreateRequest(String name, String email) {
        this.name = name.trim();
        this.email = email.trim();
    }
}
