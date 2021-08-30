package org.programmers.kdt.customer;

import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private String name;
    private String email;

    public Customer(UUID customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<< User Information >>\n");
        sb.append("Customer ID : " + customerId + "\n");
        sb.append("User Name : " + name + "\n");
        sb.append("User email : " + email);
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if ((null == obj) || (obj.getClass() != Customer.class)) {
            return false;
        }
        Customer customer = (Customer)obj;
        return customerId.equals(customer.getCustomerId());
    }

    @Override
    public int hashCode() {
        return customerId.hashCode();
    }
}
