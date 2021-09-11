package org.prgrms.kdt.domain;


import lombok.Getter;

@Getter
public class CreateCustomerRequest {

    private final String email;
    private final String name;

    public CreateCustomerRequest(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
