package me.programmers.springboot.basic.springbootbasic.command.customer;

import me.programmers.springboot.basic.springbootbasic.command.CommandStrategy;
import me.programmers.springboot.basic.springbootbasic.customer.model.Customer;
import me.programmers.springboot.basic.springbootbasic.customer.service.CustomerService;
import me.programmers.springboot.basic.springbootbasic.io.ConsoleOutput;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerFindAllCommand implements CommandStrategy {

    private final CustomerService customerService;
    private final ConsoleOutput consoleOutput;

    public CustomerFindAllCommand(CustomerService customerService, ConsoleOutput consoleOutput) {
        this.customerService = customerService;
        this.consoleOutput = consoleOutput;
    }

    @Override
    public void operateCommand() {
        List<Customer> customers = customerService.findAll();
        consoleOutput.output(customers);
    }
}
