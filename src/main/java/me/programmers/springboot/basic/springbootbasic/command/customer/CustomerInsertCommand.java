package me.programmers.springboot.basic.springbootbasic.command.customer;

import me.programmers.springboot.basic.springbootbasic.command.CommandStrategy;
import me.programmers.springboot.basic.springbootbasic.customer.model.Customer;
import me.programmers.springboot.basic.springbootbasic.customer.service.CustomerService;
import me.programmers.springboot.basic.springbootbasic.io.ConsoleInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class CustomerInsertCommand implements CommandStrategy {

    private final CustomerService customerService;

    @Autowired
    private ConsoleInput consoleInput;

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
