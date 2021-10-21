package org.prgrms.kdt.command.service;

import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.customer.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandBlacklistService implements CommandService {

    private final CustomerService customerService;

    public CommandBlacklistService(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void commandRun() {
        final List<Customer> blacklist = customerService.findAllBlacklist();
        for (final Customer customer : blacklist) {
            System.out.println(customer);
        }
    }
}
