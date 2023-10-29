package com.pgms.part1.domain.customer.entity;

public class CustomerBuilder {
    private Long id;
    private String name;
    private String email;
    private Boolean isBlocked = false;

    public CustomerBuilder id(Long id){
        this.id = id;
        return this;
    }

    public CustomerBuilder name(String name){
        this.name = name;
        return this;
    }

    public CustomerBuilder email(String email){
        this.email = email;
        return this;
    }

    public CustomerBuilder isBlocked(Boolean isBlocked){
        this.isBlocked = isBlocked;
        return this;
    }

    public Customer build(){
        return new Customer(id, name, email, isBlocked);
    }
}
