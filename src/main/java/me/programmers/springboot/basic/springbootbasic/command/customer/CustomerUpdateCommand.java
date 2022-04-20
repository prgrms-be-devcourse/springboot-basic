package me.programmers.springboot.basic.springbootbasic.command.customer;

import me.programmers.springboot.basic.springbootbasic.command.CommandStrategy;
import me.programmers.springboot.basic.springbootbasic.customer.service.CustomerService;
import me.programmers.springboot.basic.springbootbasic.io.ConsoleInput;
import me.programmers.springboot.basic.springbootbasic.io.In;

import java.util.UUID;

public class CustomerUpdateCommand implements CommandStrategy {

    private final CustomerService customerService;
    private final ConsoleInput consoleInput = new In();

    public CustomerUpdateCommand(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void operateCommand() {
        UUID uuid = UUID.fromString(consoleInput.inputCommand("update 할 고객 uuid 입력 "));
        String name = consoleInput.inputCommand("name 입력 ");
        String email = consoleInput.inputCommand("email 입력 ");

        customerService.update(uuid, name, email);
    }
}
