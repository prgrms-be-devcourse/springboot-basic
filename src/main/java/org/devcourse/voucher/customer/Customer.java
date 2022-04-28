package org.devcourse.voucher.customer;


import java.util.UUID;

public class Customer {
    private final UUID customerId;

    private String name;
    private String age;

    public Customer(UUID customerId, String name, String age) {
        this.customerId = customerId;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return  customerId + "\t" +
                name + "\t" +
                age + "\t";
    }
}
