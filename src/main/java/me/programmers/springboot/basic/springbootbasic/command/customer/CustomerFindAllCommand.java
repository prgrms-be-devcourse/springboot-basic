package me.programmers.springboot.basic.springbootbasic.command.customer;

import me.programmers.springboot.basic.springbootbasic.command.CommandStrategy;
import me.programmers.springboot.basic.springbootbasic.customer.model.Customer;
import me.programmers.springboot.basic.springbootbasic.customer.service.CustomerService;
import me.programmers.springboot.basic.springbootbasic.io.ConsoleOutput;
import me.programmers.springboot.basic.springbootbasic.io.Out;

import java.util.List;

public class CustomerFindAllCommand implements CommandStrategy {

    private final CustomerService customerService;
    private final ConsoleOutput consoleOutput = new Out();

    public CustomerFindAllCommand(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void operateCommand() {
        List<Customer> customers = customerService.findAll();
        consoleOutput.output(customers);
    }
}
