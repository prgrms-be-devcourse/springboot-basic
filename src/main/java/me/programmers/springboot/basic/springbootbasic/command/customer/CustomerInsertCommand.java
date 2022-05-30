package me.programmers.springboot.basic.springbootbasic.command.customer;

import me.programmers.springboot.basic.springbootbasic.command.CommandStrategy;
import me.programmers.springboot.basic.springbootbasic.customer.model.Customer;
import me.programmers.springboot.basic.springbootbasic.customer.model.CustomerInfo;
import me.programmers.springboot.basic.springbootbasic.customer.service.CustomerService;
import me.programmers.springboot.basic.springbootbasic.io.ConsoleInput;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class CustomerInsertCommand implements CommandStrategy {

    private final CustomerService customerService;
    private final ConsoleInput consoleInput;

    public CustomerInsertCommand(CustomerService customerService, ConsoleInput consoleInput) {
        this.customerService = customerService;
        this.consoleInput = consoleInput;
    }

    @Override
    public void operateCommand() {
        String name = consoleInput.inputCommand("이름 입력 ");
        String email = consoleInput.inputCommand("email 입력 ");

        customerService.insert(new Customer(UUID.randomUUID(), new CustomerInfo(name, email), LocalDateTime.now(), LocalDateTime.now()));
    }
}
