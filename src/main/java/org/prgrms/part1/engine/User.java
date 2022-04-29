package org.prgrms.part1.engine;

import com.opencsv.bean.CsvBindByName;

public class User {
    @CsvBindByName
    private String name;
    @CsvBindByName
    private int age;

    public User() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }
}
