package org.prgms.voucheradmin.domain.customer.dto;

public class CustomerDto {
    private Long id;
    private String name;

    public CustomerDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return id+"\t"+name;
    }
}
