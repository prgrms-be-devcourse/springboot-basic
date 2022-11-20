package prgms.vouchermanagementapp.controller;

import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.domain.Customer;
import prgms.vouchermanagementapp.service.CustomerManager;
import prgms.vouchermanagementapp.view.IoManager;

import java.util.Optional;

@Component
public class CustomerController {

    private final IoManager ioManager;
    private final CustomerManager customerManager;

    public CustomerController(IoManager ioManager, CustomerManager customerManager) {
        this.ioManager = ioManager;
        this.customerManager = customerManager;
    }

    public void run() {
        String customerName = ioManager.askCustomerName();
        Optional<Customer> customer = customerManager.findCustomerByName(customerName);
        if (customer.isEmpty()) {
            customerManager.save(new Customer(customerName));
        }
    }
}
