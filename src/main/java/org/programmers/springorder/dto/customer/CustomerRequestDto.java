package org.programmers.springorder.dto.customer;

public class CustomerRequestDto {
    private String name;

    public CustomerRequestDto() {
    }

    public CustomerRequestDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
