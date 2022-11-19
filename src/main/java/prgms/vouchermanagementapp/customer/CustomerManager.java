package prgms.vouchermanagementapp.customer;

import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.domain.Customer;
import prgms.vouchermanagementapp.storage.Customers;

import java.util.Optional;

@Component
public class CustomerManager {

    private final Customers customers;

    public CustomerManager(Customers customers) {
        this.customers = customers;
    }

    public Customer save(Customer customer) {
        return customers.save(customer);
    }

    public Optional<Customer> findCustomerByName(String name) {
        return customers.findByName(name);
    }
}
