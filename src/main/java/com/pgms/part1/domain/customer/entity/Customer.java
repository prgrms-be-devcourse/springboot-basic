package com.pgms.part1.domain.customer.entity;

public class Customer {
    private Long id;
    private String name;
    private String email;
    private Boolean isBlocked = false;

    public Customer(Long id, String name, String email, Boolean isBlocked) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.isBlocked = isBlocked;
    }

    public Long getId() {
        return id;
    }

    public Boolean getBlocked() {
        return isBlocked;
    }
}
