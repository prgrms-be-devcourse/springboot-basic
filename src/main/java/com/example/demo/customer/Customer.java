package com.example.demo.customer;

import com.example.demo.exception.WrongCustomerParamsException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class Customer {
    public static Customer EMPTY = new Customer(new ArrayList<String>(Arrays.asList({"0", "0", "0", "0"})));
    private String name;
    private int age;
    private String description;
    private Sex sex;

    public enum Sex {
        MEN("남자"),
        WOMEN("여자");

        private final String sex;
        private static final Map<String, Sex> sexHashMap = Stream.of(values()).collect(toMap(Object::toString, (e)-> e));

        Sex(String sex) {
            this.sex = sex;
        }

        @Override
        public String toString() {
            return sex;
        }

        public static Optional<Sex> fromString(String sex) {
            return Optional.ofNullable(sexHashMap.get(sex));
        }
    }

    public Customer(ArrayList<String> params) throws WrongCustomerParamsException {
        this.name = params.get(0);
        this.age = Integer.getInteger(params.get(1));
        this.description = params.get(2);
        this.sex = Sex.fromString(params.get(3)).orElseThrow(()-> new WrongCustomerParamsException("올바른 고객 정보가 아닙니다."));
    }

    @Override
    public String toString() {
        return "name: " + name + ' ' +
                "age: " + age +  " " +
                "description: " + description + " "+
                "sex: " + sex;
    }
}
