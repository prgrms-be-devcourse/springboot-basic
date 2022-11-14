package org.programmers.springbootbasic.domain;

public class Customer {
    private long id;
    private String name;

    public Customer(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "id : " + id + ", name : " + name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
