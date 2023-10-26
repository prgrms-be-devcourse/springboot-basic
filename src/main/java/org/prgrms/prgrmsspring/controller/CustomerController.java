package org.prgrms.prgrmsspring.controller;

import org.prgrms.prgrmsspring.domain.command.Command;
import org.prgrms.prgrmsspring.domain.command.CustomerCommand;
import org.prgrms.prgrmsspring.entity.user.Customer;
import org.prgrms.prgrmsspring.service.CustomerService;
import org.prgrms.prgrmsspring.view.CommandLineView;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class CustomerController implements ApplicationController {
    private final CommandLineView commandLineView;
    private final CustomerService customerService;

    public CustomerController(CommandLineView commandLineView, CustomerService customerService) {
        this.commandLineView = commandLineView;
        this.customerService = customerService;
    }

    public void findAll() {
        List<Customer> allCustomers = customerService.findAll();
        commandLineView.printAll(allCustomers);
    }

    public void findAllBlackList() {
        List<Customer> blackCustomers = customerService.findBlackAll();
        commandLineView.printAll(blackCustomers);
    }

    public void create() {
        Customer customer = commandLineView.createCustomer();
        customerService.create(customer);
    }

    public void update() {
        Customer updateCustomer = commandLineView.updateCustomer();
        customerService.update(updateCustomer);
    }

    public void delete() {
        UUID deleteCustomerId = commandLineView.deleteCustomer();
        customerService.delete(deleteCustomerId);
    }

    @Override
    public void run(String commandName) {
        Command command = Command.from(commandName, CustomerCommand.class);
        command.run(this);
    }

    @Override
    public void listMode() {
        commandLineView.printAll(ApplicationController.getModeStrings(CustomerCommand.values()));
    }
}
