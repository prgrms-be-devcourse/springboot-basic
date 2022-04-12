package com.kdt.commandLineApp.customer;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class Customer {
    private String name;
    private int age;
    private String description;
    private Sex sex;

    public Customer(ArrayList<String> params) {
        this.name = params.get(0);
        this.age = Integer.parseInt(params.get(1));
        this.sex = Sex.fromString(params.get(2));
        this.description = params.get(3);
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

        public static Sex fromString(String sex) {
            return sexHashMap.get(sex);
        }
    }

    @Override
    public String toString() {
        return "name: " + name +
                " age: " + age +
                " sex: " + sex +
                " description: " + description;
    }
}
