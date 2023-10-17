package org.prgms.kdtspringweek1.customer.entity;

import java.text.MessageFormat;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String name;
    private boolean isBlackCustomer = false;

    public Customer(UUID customerId, String name, boolean isBlackCustomer) {
        this.customerId = customerId;
        this.name = name;
        this.isBlackCustomer = isBlackCustomer;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", isBlackCustomer=" + isBlackCustomer +
                '}';
    }

    public void printCustomerInfo() {
        System.out.println(MessageFormat.format("Customer Id: {0}", customerId));
        System.out.println(MessageFormat.format("Name: {0}", name));
        System.out.println(MessageFormat.format("IsBlackCustomer: {0}", isBlackCustomer));
    }
}
