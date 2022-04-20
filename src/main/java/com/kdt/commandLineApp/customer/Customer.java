package com.kdt.commandLineApp.customer;

import com.kdt.commandLineApp.exception.WrongCustomerParamsException;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Customer {
    private String name;
    private int age;
    private Sex sex;

    public Customer(String name, String age, String sex) throws WrongCustomerParamsException {
        this.name = name;
        this.age = Integer.parseInt(age);
        if (this.age < 0) {
            throw new WrongCustomerParamsException();
        }
        this.sex = Sex.fromString(sex);
    }

    public Customer(String name, int age, String sex) throws WrongCustomerParamsException {
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
                " sex: " + sex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return age == customer.age && Objects.equals(name, customer.name) && sex == customer.sex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, sex);
    }

    public String getSex() {
        return this.sex.toString();
    }
}
