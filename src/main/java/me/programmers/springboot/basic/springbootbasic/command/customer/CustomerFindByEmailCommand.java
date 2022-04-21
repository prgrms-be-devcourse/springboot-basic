package me.programmers.springboot.basic.springbootbasic.command.customer;

import me.programmers.springboot.basic.springbootbasic.command.CommandStrategy;
import me.programmers.springboot.basic.springbootbasic.customer.model.Customer;
import me.programmers.springboot.basic.springbootbasic.customer.service.CustomerService;
import me.programmers.springboot.basic.springbootbasic.io.ConsoleInput;
import me.programmers.springboot.basic.springbootbasic.io.ConsoleOutput;
import me.programmers.springboot.basic.springbootbasic.io.In;
import me.programmers.springboot.basic.springbootbasic.io.Out;

public class CustomerFindByEmailCommand implements CommandStrategy {

    private final CustomerService customerService;
    private final ConsoleInput consoleInput = new In();
    private final ConsoleOutput consoleOutput = new Out();

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
