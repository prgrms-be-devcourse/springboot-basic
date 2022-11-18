package org.prgrms.kdt.customer;

import org.springframework.beans.factory.annotation.Autowired;

public class Customer {

    private final long id;
    private final String name;

    public Customer(String id, String name) {
        this(getParseLong(id), name);
    }

    private static long getParseLong(String id) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException exception) {
            throw new NumberFormatException("Wrong Customer Id. Id : " + id);
        }
    }

    public Customer(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
