package prgms.vouchermanagementapp.customer;

import prgms.vouchermanagementapp.storage.Customers;

import java.util.Optional;

public class CustomerManager {

    private final Customers customers;

    private Customer currentCustomer;

    public CustomerManager(Customers customers) {
        this.customers = customers;
    }

    public void save(Customer customer) {
        this.currentCustomer = customers.save(customer);
    }

    public Optional<Customer> findCustomerByName(String name) {
        return customers.findByName(name);
    }

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }
}
