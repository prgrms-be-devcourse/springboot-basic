package com.programmers.part1.domain.customer;

import lombok.Getter;

@Getter
public class CustomerDto {
    private long id;
    private String name;
    private String CustomerType;

    public CustomerDto(long id, String name, String customerType) {
        this.id = id;
        this.name = name;
        this.CustomerType = customerType;
    }

    @Override
    public String toString() {
        return String.format("ID: %-4d Name: %-10s customerType: %-8s",this.id, this.name, this.CustomerType);
    }
}
