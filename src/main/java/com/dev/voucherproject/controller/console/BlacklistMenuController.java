package com.dev.voucherproject.controller.console;

import com.dev.voucherproject.model.customer.Customer;
import com.dev.voucherproject.model.customer.CustomerDto;
import com.dev.voucherproject.model.storage.blacklist.BlacklistStorage;
import com.dev.voucherproject.view.Console;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BlacklistMenuController implements MenuController {
    private final Console console;
    private final BlacklistStorage blacklistStorage;

    public BlacklistMenuController(Console console, BlacklistStorage blacklistStorage) {
        this.console = console;
        this.blacklistStorage = blacklistStorage;
    }

    @Override
    public void execute() {
        List<Customer> customers = blacklistStorage.findAll();

        List<CustomerDto> dtos = customers.stream()
                .map(CustomerDto::fromCustomer)
                .toList();

        console.printAllCustomers(dtos);
    }
}
