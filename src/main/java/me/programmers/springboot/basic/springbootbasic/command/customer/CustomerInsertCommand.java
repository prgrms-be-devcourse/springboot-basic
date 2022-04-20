package me.programmers.springboot.basic.springbootbasic.command.customer;

import me.programmers.springboot.basic.springbootbasic.command.CommandStrategy;
import me.programmers.springboot.basic.springbootbasic.customer.model.Customer;
import me.programmers.springboot.basic.springbootbasic.customer.service.CustomerService;
import me.programmers.springboot.basic.springbootbasic.io.ConsoleInput;
import me.programmers.springboot.basic.springbootbasic.io.In;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerInsertCommand implements CommandStrategy {

    private final ConsoleInput consoleInput = new In();
    private final CustomerService customerService;

    public CustomerInsertCommand(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void operateCommand() {
        String name = consoleInput.inputCommand("이름 입력 ");
        String email = consoleInput.inputCommand("email 입력 ");

        customerService.insert(new Customer(UUID.randomUUID(), name, email, LocalDateTime.now(), LocalDateTime.now()));
    }
}
