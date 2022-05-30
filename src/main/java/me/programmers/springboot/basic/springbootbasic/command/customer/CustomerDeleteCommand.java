package me.programmers.springboot.basic.springbootbasic.command.customer;

import me.programmers.springboot.basic.springbootbasic.command.CommandStrategy;
import me.programmers.springboot.basic.springbootbasic.customer.service.CustomerService;
import org.springframework.stereotype.Component;

@Component
public class CustomerDeleteCommand implements CommandStrategy {

    private final CustomerService customerService;

    public CustomerDeleteCommand(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void operateCommand() {
        customerService.delete();
    }
}
