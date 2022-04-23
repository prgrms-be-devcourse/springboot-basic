package org.prgrms.springbootbasic.entity.customer;

public class Name {

    private String name;

    public Name(String name) {
        this.name = name;
    }

    public void changeName(String newName) {
        name = newName;
    }

    public String showName() {
        return name;
    }
}
