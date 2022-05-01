package me.programmers.springboot.basic.springbootbasic.command.customer;

import me.programmers.springboot.basic.springbootbasic.command.CommandStrategy;
import me.programmers.springboot.basic.springbootbasic.customer.model.Customer;
import me.programmers.springboot.basic.springbootbasic.customer.service.CustomerService;
import me.programmers.springboot.basic.springbootbasic.io.ConsoleInput;
import me.programmers.springboot.basic.springbootbasic.io.ConsoleOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerFindByEmailCommand implements CommandStrategy {

    private final CustomerService customerService;

    @Autowired
    private ConsoleInput consoleInput;
    @Autowired
    private ConsoleOutput consoleOutput;

    public CustomerFindByEmailCommand(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void operateCommand() {
        String email = consoleInput.inputCommand("email 입력 ");
        Customer customer = customerService.findByEmail(email);
        consoleOutput.output(customer.toString());
    }
}
