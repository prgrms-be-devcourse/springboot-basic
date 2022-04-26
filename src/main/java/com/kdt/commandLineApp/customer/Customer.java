package com.kdt.commandLineApp.customer;

import com.kdt.commandLineApp.exception.WrongCustomerParamsException;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
public class Customer {
    private UUID customerId;
    private String name;
    private int age;
    private Sex sex;

    public Customer(String name, String age, String sex) throws WrongCustomerParamsException {
        this(UUID.randomUUID(), name, Integer.parseInt(age), sex);
    }

    public Customer(UUID customerId, String name, int age, String sex) throws WrongCustomerParamsException {
        this.customerId = customerId;
        this.name = name;
        this.age = age;
        if (this.age < 0) {
            throw new WrongCustomerParamsException();
        }
        this.sex = Sex.fromString(sex);
    }


    @Override
    public String toString() {
        return "name: " + name +
                " age: " + age +
                " sex: " + sex ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(customerId, customer.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId);
    }

    public String getSex() {
        return this.sex.toString();
    }
}
