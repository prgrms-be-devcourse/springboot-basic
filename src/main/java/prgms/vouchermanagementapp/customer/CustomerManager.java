package prgms.vouchermanagementapp.customer;

import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.storage.Customers;

@Component
public class CustomerManager {

    private final Customers customers;

    private Customer currentCustomer;

    public CustomerManager(Customers customers) {
        this.customers = customers;
    }

    public void findCustomerByName() {
        // TODO: find customer from database by name
    }

    public void save() {
        // TODO: save customer to database
    }
}
