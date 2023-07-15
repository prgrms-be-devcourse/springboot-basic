package com.programmers.springmission.customer.presentation.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerUpdateRequest {

    private String name;

    public CustomerUpdateRequest(String name) {
        this.name = name.trim();
    }
}
