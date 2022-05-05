package org.prgrms.springbootbasic.engine.controller.dto;

public class CustomerCreateRequestDto {
    private final String name;
    private final String email;

    public CustomerCreateRequestDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
