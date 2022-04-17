package com.kdt.commandLineApp.customer;

import com.kdt.commandLineApp.exception.WrongCustomerParamsException;
import lombok.Getter;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

@Getter
public class Customer {
    private UUID customerId;
    private String name;
    private int age;
    private Sex sex;

    public Customer(ArrayList<String> params) throws WrongCustomerParamsException {
        this.name = params.get(0);
        this.age = Integer.parseInt(params.get(1));
        if (this.age < 0) {
            throw new WrongCustomerParamsException();
        }
        this.sex = Sex.fromString(params.get(2));
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

    public Customer(String name, int age, String sex) throws WrongCustomerParamsException {
        this(UUID.randomUUID(), name, age, sex);
    }

    public enum Sex {
        MEN("man"),
        WOMEN("woman");

        private final String sex;
        private static final Map<String, Sex> sexHashMap = Stream.of(values()).collect(toMap(Object::toString, (e)-> e));

        Sex(String sex) {
            this.sex = sex;
        }

        @Override
        public String toString() {
            return sex;
        }

        public static Sex fromString(String sex) throws WrongCustomerParamsException {
            return Optional.ofNullable(sexHashMap.get(sex)).orElseThrow(()-> new WrongCustomerParamsException());
        }
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
        if (o == null || getClass() != o.getClass()) return false;
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
