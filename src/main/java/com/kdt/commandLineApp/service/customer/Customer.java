package com.kdt.commandLineApp.service.customer;

import com.kdt.commandLineApp.exception.WrongCustomerParamsException;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
public class Customer {
    private long id;
    private String name;
    private int age;
    private Sex sex;

    public Customer(String name, String age, String sex) throws WrongCustomerParamsException {
        this.name = name;
        this.age =  Integer.parseInt(age);
        if (this.age < 0) {
            throw new WrongCustomerParamsException();
        }
        this.sex = Sex.fromString(sex);
    }

    public Customer(long customerId, String name, int age, String sex) throws WrongCustomerParamsException {
        this.id = customerId;
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
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getSex() {
        return this.sex.toString();
    }
}
