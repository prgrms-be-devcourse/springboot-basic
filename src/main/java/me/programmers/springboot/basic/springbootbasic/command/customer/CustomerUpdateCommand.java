package me.programmers.springboot.basic.springbootbasic.command.customer;

import me.programmers.springboot.basic.springbootbasic.command.CommandStrategy;
import me.programmers.springboot.basic.springbootbasic.customer.service.CustomerService;
import me.programmers.springboot.basic.springbootbasic.io.ConsoleInput;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerUpdateCommand implements CommandStrategy {

    private final CustomerService customerService;
    private final ConsoleInput consoleInput;

    public CustomerUpdateCommand(CustomerService customerService, ConsoleInput consoleInput) {
        this.customerService = customerService;
        this.consoleInput = consoleInput;
    }

    @Override
    public void operateCommand() {
        UUID uuid = UUID.fromString(consoleInput.inputCommand("update 할 고객 uuid 입력 "));
        String name = consoleInput.inputCommand("name 입력 ");
        String email = consoleInput.inputCommand("email 입력 ");

        customerService.update(uuid, name, email);
    }
}
