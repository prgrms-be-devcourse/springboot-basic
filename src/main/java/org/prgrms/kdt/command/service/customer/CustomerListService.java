package org.prgrms.kdt.command.service.customer;

import org.prgrms.kdt.command.service.CommandService;
import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.customer.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerListService implements CommandService {

    private final CustomerService customerService;

    public CustomerListService(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void commandRun() {
        final List<Customer> customerList = customerService.getAllCustomer();
        for (final Customer customer : customerList) {
            System.out.println(customer);
        }
    }
}
