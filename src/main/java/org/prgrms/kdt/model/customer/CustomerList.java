package org.prgrms.kdt.model.customer;

import org.prgrms.kdt.io.OutputConsole;

import java.util.List;

public class CustomerList {
    private final List<Customer> customers;

    public CustomerList(List<Customer> customers) {
        this.customers = customers;
    }
    public boolean isEmptyList() {
        return customers.isEmpty();
    }
    public int size() {
        return customers.size();
    }

    public void printList() {
        customers.forEach(customer -> {
            OutputConsole.printMessage(customer.getCustomerId().toString());
        });
    }
}
