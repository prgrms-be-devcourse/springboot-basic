package com.prgms.vouchermanager.domain.customer;


import lombok.Getter;
@Getter
public class Customer {

    private Long id;

    private final String name;

    private final String email;

    private final boolean blackList;

    public Customer(Long id, String name, String email, boolean blackList) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.blackList = blackList;

    }

    public void setId(long longValue) {
        this.id = longValue;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", blackList=" + blackList +
                '}';
    }
}
