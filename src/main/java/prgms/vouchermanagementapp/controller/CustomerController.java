package prgms.vouchermanagementapp.controller;

import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.domain.Customer;
import prgms.vouchermanagementapp.service.CustomerService;
import prgms.vouchermanagementapp.view.IoManager;

import java.util.Optional;

@Component
public class CustomerController {

    private final IoManager ioManager;
    private final CustomerService customerService;

    public CustomerController(IoManager ioManager, CustomerService customerService) {
        this.ioManager = ioManager;
        this.customerService = customerService;
    }

    public void run() {
        String customerName = ioManager.askCustomerName();
        Optional<Customer> customer = customerService.findCustomerByName(customerName);
        if (customer.isEmpty()) {
            customerService.save(new Customer(customerName));
        }
    }
}
