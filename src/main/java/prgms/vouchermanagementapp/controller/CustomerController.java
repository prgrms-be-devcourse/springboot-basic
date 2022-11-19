package prgms.vouchermanagementapp.controller;

import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.customer.CustomerManager;
import prgms.vouchermanagementapp.domain.Customer;
import prgms.vouchermanagementapp.io.IoManager;
import prgms.vouchermanagementapp.storage.Customers;

import java.util.Optional;

@Component
public class CustomerController {

    private final IoManager ioManager;
    private final CustomerManager customerManager;

    public CustomerController(IoManager ioManager, Customers customers) {
        this.ioManager = ioManager;
        this.customerManager = new CustomerManager(customers);
    }

    public void run() {
        String customerName = ioManager.askCustomerName();
        Optional<Customer> customer = customerManager.findCustomerByName(customerName);
        if (customer.isEmpty()) {
            customerManager.save(new Customer(customerName));
        }
    }
}
