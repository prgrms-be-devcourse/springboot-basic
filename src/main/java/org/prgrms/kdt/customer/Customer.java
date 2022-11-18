package org.prgrms.kdt.customer;

public class Customer {

    private final long id;
    private final String name;

    public Customer(String id, String name) {
        try {
            this.id = Long.parseLong(id);
        } catch (NumberFormatException exception) {
            throw new NumberFormatException("Wrong Customer Id. Id : " + id);
        }
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
