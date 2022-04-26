package org.prgrms.part1.engine.controller.dto;

public class CustomerCreateRequestDto {
    private String name;
    private String email;

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
