package org.prgrms.prgrmsspring.controller;

import org.prgrms.prgrmsspring.domain.Command;
import org.prgrms.prgrmsspring.entity.user.Customer;
import org.prgrms.prgrmsspring.service.CustomerService;
import org.prgrms.prgrmsspring.view.CommandLineView;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CustomerController implements ApplicationController {
    private final CommandLineView commandLineView;
    private final CustomerService customerService;

    public CustomerController(CommandLineView commandLineView, CustomerService customerService) {
        this.commandLineView = commandLineView;
        this.customerService = customerService;
    }

    public void showBlackList() {
        List<Customer> blackCustomers = customerService.findBlackAll();
        commandLineView.printAll(blackCustomers);
    }

    @Override
    public void start(Command command) {
        command.run(this);
    }
}
